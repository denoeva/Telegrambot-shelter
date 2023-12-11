package pro.sky.telegrambot.shelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.shelter.exceptions.AnimalNotFoundException;
import pro.sky.telegrambot.shelter.exceptions.PhotoNotFoundException;
import pro.sky.telegrambot.shelter.exceptions.ReportNotFoundException;
import pro.sky.telegrambot.shelter.exceptions.VolunteerNotFoundException;
import pro.sky.telegrambot.shelter.model.*;
import pro.sky.telegrambot.shelter.repository.*;
import pro.sky.telegrambot.shelter.model.Animal;
import pro.sky.telegrambot.shelter.model.Photo;
import pro.sky.telegrambot.shelter.model.Users;
import pro.sky.telegrambot.shelter.model.Volunteer;
import pro.sky.telegrambot.shelter.repository.AnimalRepository;
import pro.sky.telegrambot.shelter.repository.PhotoRepository;
import pro.sky.telegrambot.shelter.repository.UserRepository;
import pro.sky.telegrambot.shelter.repository.VolunteerRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pro.sky.telegrambot.shelter.model.Info.*;

/**
 * Class to process all incoming messages from Telegram
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d{11})\\s+(.*)");
    private static final Pattern REPORT_PATTER = Pattern.compile("(.*)\\n+(1.*)\\n+(2.*)\\n+(3.*)");
    private static final Pattern HELP_VOLUNTEER = Pattern.compile("(@.*)\\n+(.*)");
    private boolean nextUpdateIsUserContacts = false;
    private boolean nextUpdateIsHelpVolunteer = false;
    private boolean nextUpdateIsReport = false;
    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Method to process all incoming messages from Telegram
     *
     * @param updates all updates from bot
     * @return <code>UpdatesListener.CONFIRMED_UPDATES_ALL</code>
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            Long chatId = extractChatId(update);
            String message = extractMessage(update, chatId);
            if (message == null){
                telegramBot.execute(new SendMessage(chatId, "Нет текста в сообщении"));
                return ;
            }
            if (nextUpdateIsReport){
                nextUpdateIsReport = false;
                Matcher report_matcher = REPORT_PATTER.matcher(message);
                // Проверяем отчет
                if (!report_matcher.matches() || update.message().photo() == null) {
                    telegramBot.execute(new SendMessage(chatId, CONFLICT_REPORT).replyMarkup(ConflictReportInlineKeyboard()));
                } else if(report_matcher.matches()) {
                    // Сохранение и отправка отчета(фото+текст) волонтеру
                    PhotoSize[] photosize = update.message().photo();

                    Report report = new Report();
                    report.setReport(message);
                    report.setDateTime(LocalDateTime.now());
                    report.setChatId(chatId);
                    report.setAnimal(userRepository.findAnimal(chatId));

                    GetFileResponse responsePhoto = telegramBot.execute(new GetFile(photosize[photosize.length - 1].fileId()));

                    ReportPhoto reportPhoto = new ReportPhoto();
                    reportPhoto.setReport(report);
                    byte[] photo;
                    try {
                        photo = telegramBot.getFileContent(responsePhoto.file());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    reportPhoto.setReportPhotoData(photo);
                    report.setReportPhoto(reportPhoto);
                    report = reportRepository.save(report);
                    telegramBot.execute(new SendMessage(chatId, REPORT_ACCEPTED_FOR_CHECKING).replyMarkup(ConflictReportInlineKeyboard()));
                    // Ищем в базе волонтера и отправляем ему отчет
                    Volunteer volunteer = volunteerRepository.findAll().stream().findAny().orElseThrow(VolunteerNotFoundException::new);
                    SendPhoto sendPhoto = new SendPhoto(volunteer.getChatId(), photo).caption(NEW_REPORT
                            + prepareReportForVolunteer(update, chatId, report)).replyMarkup(prepareVolunteerInlineKeyboard());
                    telegramBot.execute(sendPhoto);
                }
            }
            // Сохранение данных пользователя
            if (nextUpdateIsUserContacts) {
                nextUpdateIsUserContacts = false;
                Matcher matcher = NUMBER_PATTERN.matcher(message);
                // Проверка на правильность вводимых данных
                if (!matcher.matches()) {
                    SendMessage ConflictSave = new SendMessage(chatId,CONFLICT_USER_CONTACT)
                            .replyMarkup(ConflictSaveUserInlineKeyboard());
                    telegramBot.execute(ConflictSave);
                } else if (matcher.matches()){
                    String phoneNumber = matcher.group(1);
                    String name = matcher.group(2);
                    Users users = new Users();
                    users.setChatId(chatId);
                    users.setPhoneNumber(phoneNumber);
                    users.setName(name);
                    userRepository.save(users);
                    SendMessage CompleteSave = new SendMessage(chatId,COMPLETE_USER_CONTACT).replyMarkup(StepBackStartingInlineKeyboard());
                    telegramBot.execute(CompleteSave);
                }
            }
            // Позвать на помощь волонтера
            if (nextUpdateIsHelpVolunteer) {
                nextUpdateIsHelpVolunteer = false;
                Matcher matcher = HELP_VOLUNTEER.matcher(message);
                // Проверка на правильность оформления запроса
                if (matcher.matches()){
                    String userName = matcher.group(1);
                    String problem = matcher.group(2);
                    // Поиск волонтера; Ответное сообщение волонтеру (логин и вопрос пользователя)
                    Volunteer volunteer = volunteerRepository.findAll().stream().findAny().orElseThrow(VolunteerNotFoundException::new);
                    SendMessage helpMessage = new SendMessage(volunteer.getChatId(),
                            "\u2753Нужна помощь\u2753\nПользователю: " + userName + ",\nпо вопросу: " + problem);
                    SendMessage completeHelp = new SendMessage(chatId,HELP_END).replyMarkup(StepBackStartingInlineKeyboard());
                    telegramBot.execute(helpMessage);
                    telegramBot.execute(completeHelp);
                } else if (!matcher.matches()) {
                    SendMessage conflictHelpMessage = new SendMessage(chatId, HELP_CONFLICT)
                            .replyMarkup(ConflictHelpVolunteerInlineKeyboard());
                    telegramBot.execute(conflictHelpMessage);
                }
            }
            switch (message) {
                case "/start":
                    SendMessage sendMessage = new SendMessage(chatId, WELCOME_MESSAGE).replyMarkup(prepareStartingInlineKeyBoard());
                    telegramBot.execute(sendMessage);
                    break;
                case "/schedule":
                    SendMessage schedule = new SendMessage(chatId, SCHEDULE_AND_ADDRESS).replyMarkup(StepBackStartingInlineKeyboard());
                    telegramBot.execute(schedule);
                    break;
                case "/info":
                    SendMessage info = new SendMessage(chatId, SHELTER_INFO).replyMarkup(prepareInfoInlineKeyBoard());
                    telegramBot.execute(info);
                    break;
                case "/contacts":
                    SendMessage contactInfo = new SendMessage(chatId, CONTACTS).replyMarkup(StepBackStartingInlineKeyboard());
                    telegramBot.execute(contactInfo);
                    break;
                case "/rules":
                    SendMessage rulesInfo = new SendMessage(chatId, RULES).replyMarkup(prepareRulesInlineKeyBoard());
                    telegramBot.execute(rulesInfo);
                    break;
                case "/docs":
                    SendMessage docsInfo = new SendMessage(chatId, DOCS).replyMarkup(StepBackStartingInlineKeyboard());
                    telegramBot.execute(docsInfo);
                    break;
                case "/recommends":
                    SendMessage recommendsInfo = new SendMessage(chatId, RECOMMENDATIONS).replyMarkup(StepBackStartingInlineKeyboard());
                    telegramBot.execute(recommendsInfo);
                    break;
                case "/save":
                    SendMessage safetyInstructions = new SendMessage(chatId, SAVE_INSTRUCTIONS).replyMarkup(StepBackStartingInlineKeyboard());
                    telegramBot.execute(safetyInstructions);
                    break;
                case "/reject":
                    SendMessage reasonsForRejections = new SendMessage(chatId, REASONS_FOR_REJECTION).replyMarkup(StepBackStartingInlineKeyboard());
                    telegramBot.execute(reasonsForRejections);
                    break;
                case "/tipsFromDogHandler":
                    SendMessage tipsFromDogHandlerInfo = new SendMessage(chatId, TIPS_FOR_DOG_HANDLER).replyMarkup(StepBackStartingInlineKeyboard());
                    telegramBot.execute(tipsFromDogHandlerInfo);
                    break;
                case "/recommendationsForProvenDogHandlers":
                    SendMessage recommendationsForProvenDogHandlersInfo = new SendMessage(chatId, RECS_FOR_PROVEN_DOG_HANDLER).replyMarkup(StepBackStartingInlineKeyboard());
                    telegramBot.execute(recommendationsForProvenDogHandlersInfo);
                    break;
                case "/reasonsForRefusal":
                    SendMessage reasonsForRefusingTakeDogFromShelter = new SendMessage(chatId, REASONS_FOR_REFUSAL).replyMarkup(StepBackStartingInlineKeyboard());
                    telegramBot.execute(reasonsForRefusingTakeDogFromShelter);
                    break;
                case "/animals":
                    SendMessage animals = new SendMessage(chatId, ANIMALS).replyMarkup(prepareAnimalsInlineKeyBoard());
                    telegramBot.execute(animals);
                    break;
                case "/cats":
                    animalRepository.findAnimalsByAttachedFalseAndTypeOfAnimal(Animal.TypeOfAnimal.CAT).forEach(
                            animal -> {
                                try {
                                    Photo animalPhoto = photoRepository.findFirstByAnimal(animal).orElseThrow(PhotoNotFoundException::new);
                                    SendPhoto photo = new SendPhoto(chatId, animalPhoto.getData()).caption(prepareAnimalForBot(animal)).replyMarkup(prepareAnimaChosenInlineKeyboard());
                                    telegramBot.execute(photo);
                                } catch (PhotoNotFoundException ignored) {
                                    SendMessage messageWithOutPhoto = new SendMessage(chatId, prepareAnimalForBot(animal)).replyMarkup(prepareAnimaChosenInlineKeyboard());
                                    telegramBot.execute(messageWithOutPhoto);
                                }
                            }
                    );
                    break;
                case "/dogs":
                    animalRepository.findAnimalsByAttachedFalseAndTypeOfAnimal(Animal.TypeOfAnimal.DOG).forEach(
                            animal -> {
                                try {
                                    Photo animalPhoto = photoRepository.findFirstByAnimal(animal).orElseThrow(PhotoNotFoundException::new);
                                    SendPhoto photo = new SendPhoto(chatId, animalPhoto.getData()).caption(prepareAnimalForBot(animal)).replyMarkup(prepareAnimaChosenInlineKeyboard());
                                    telegramBot.execute(photo);
                                } catch (PhotoNotFoundException ignored) {
                                    SendMessage messageWithOutPhoto = new SendMessage(chatId, prepareAnimalForBot(animal)).replyMarkup(prepareAnimaChosenInlineKeyboard());
                                    telegramBot.execute(messageWithOutPhoto);
                                }
                            }
                    );
                    break;
                case "/save_user":
                    SendMessage ContactTemplate = new SendMessage(chatId,USER_CONTACT);
                    nextUpdateIsUserContacts = true;
                    telegramBot.execute(ContactTemplate);
                    break;
                case "/take_care":
                    String animalName = update.callbackQuery().message().caption().lines().filter(line -> line.startsWith("Имя"))
                            .map(line -> StringUtils.removeStart(line, "Имя: ")).findFirst().orElseThrow(AnimalNotFoundException::new);
                    Animal animalToAttach = animalRepository.findAnimalByName(animalName);
                    Users userToAttach = userRepository.findUserByChatId(chatId);
                    animalToAttach.setAttached(true);
                    animalToAttach.setUser(userToAttach);
                    userToAttach.setAnimal(animalToAttach);
                    animalRepository.save(animalToAttach);
                    userRepository.save(userToAttach);
                    SendMessage attached = new SendMessage(chatId, INFO_AFTER_ATTACHMENT).replyMarkup(ConflictReportInlineKeyboard());
                    telegramBot.execute(attached);
                    break;
                case "/help":
                    SendMessage HelpVolunteer = new SendMessage(chatId, HELP_START);
                    nextUpdateIsHelpVolunteer = true;
                    telegramBot.execute(HelpVolunteer);
                    break;
                case "/report":
                    SendMessage reportForm = new SendMessage(chatId, REPORT_FORM).replyMarkup(StepBackStartingInlineKeyboard());
                    nextUpdateIsReport = true;
                    telegramBot.execute(reportForm);
                    break;
                case "/accept_report":
                    String reportId = update.callbackQuery().message().caption().lines().filter(line -> line.startsWith("Идентификатор")).map(line -> StringUtils.removeStart(line, "Идентификатор отчета: ")).findFirst().orElseThrow();
                    Report reportToUpdate = reportRepository.findById(Long.valueOf(reportId)).orElseThrow(ReportNotFoundException::new);
                    reportToUpdate.setCheckedByVolunteer(true);
                    SendMessage messageToVolunteer = new SendMessage(reportToUpdate.getChatId(), "\uD83D\uDDD2 Отчет принят");
                    reportRepository.save(reportToUpdate);
                    telegramBot.execute(messageToVolunteer);
                    break;
                case "/decline_report":
                    String reportToImproveId = update.callbackQuery().message().caption().lines().filter(line -> line.startsWith("Идентификатор")).map(line -> StringUtils.removeStart(line, "Идентификатор отчета: ")).findFirst().orElseThrow();
                    Report reportToImprove = reportRepository.findById(Long.valueOf(reportToImproveId)).orElseThrow(ReportNotFoundException::new);
                    SendMessage messageToUser = new SendMessage(reportToImprove.getChatId(), REPORT_REJECTED).replyMarkup(ConflictReportInlineKeyboard());
                    telegramBot.execute(messageToUser);
                    break;
                case "/adoption_decision":
                    Volunteer volunteer = volunteerRepository.findAll().stream().findAny().orElseThrow(VolunteerNotFoundException::new);
                    Users user = userRepository.findUserByChatId(chatId);
                    SendMessage forVolunteer = new SendMessage(volunteer.getChatId(),"\uD83D\uDE4F \uD83D\uDE4F \uD83D\uDE4F \nДорогой волонтер,"
                            + "\nУ пользователя: " + user.getName() + ",\nс номером: " + user.getPhoneNumber() +"\n"
                            + END_TRIAL_PERIOD_FOR_VOLUNTEER + "отдавать питомца: " + user.getAnimal().getName()).replyMarkup(adoptionDecision());
                    telegramBot.execute(forVolunteer);
                    break;
                case "/decision_to_refuse":
                    Animal animalReturnToShelter = userRepository.findAnimal(chatId);
                    Users userNotToAttachForAnimal = userRepository.findUserByChatId(chatId);
                    animalReturnToShelter.setAttached(false);
                    animalReturnToShelter.setUser(null);
                    userNotToAttachForAnimal.setAnimal(null);
                    animalRepository.save(animalReturnToShelter);
                    userRepository.save(userNotToAttachForAnimal);
                    SendMessage refused = new SendMessage(chatId, END_TRIAL_PERIOD_FOR_USER_BADLY).replyMarkup(ConflictHelpVolunteerInlineKeyboard());
                    telegramBot.execute(refused);
                    break;
                case "/decision_for_confirmation":
                    Users us = userRepository.findUserByChatId(chatId);
                    SendMessage congratulation = new SendMessage(chatId, END_TRIAL_PERIOD_FOR_USER_SUCCESS + "теперь питомец: " + us.getAnimal().getName() + " - \nчасть вашей семьи \uD83D\uDC6A");
                    telegramBot.execute(congratulation);
                    break;
                case "/decision_to_extend_probation_period":
                    Volunteer vol = volunteerRepository.findAll().stream().findAny().orElseThrow(VolunteerNotFoundException::new);
                    SendMessage extendDays = new SendMessage(vol.getChatId(),"\uD83D\uDDD3 Выберите на сколько дней продлить испытательный срок:").replyMarkup(extendProbationPeriod());
                    telegramBot.execute(extendDays);
                    break;
                case "/extend_by_14_days":
                    SendMessage extendBy14Days = new SendMessage(chatId, END_TRIAL_PERIOD_FOR_USER_EXTENSION_TIME + "14 дней").replyMarkup(ConflictReportInlineKeyboard());
                    telegramBot.execute(extendBy14Days);
                    break;
                case "/extend_by_30_days":
                    SendMessage extendBy30Days = new SendMessage(chatId, END_TRIAL_PERIOD_FOR_USER_EXTENSION_TIME + "30 дней").replyMarkup(ConflictReportInlineKeyboard());
                    telegramBot.execute(extendBy30Days);
                    break;
                default:
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Method prepares starting inline keyboard
     *
     * @param animal animal entity from database
     * @return representation of animal to send in message
     */
    private String prepareAnimalForBot(Animal animal) {
        return "Имя: " + animal.getName() + "\nПорода: " + animal.getBreed() + "\nПол: " + animal.getGender() +
                "\nОкрас: " + animal.getColor() + "\nДата рождения: " + animal.getDOB().format(DateTimeFormatter.ISO_LOCAL_DATE) +
                "\nСостояние здоровья: " + animal.getHealth() + "\nХарактер: " + animal.getCharacteristic();
    }
    /**
     * Method prepares starting inline keyboard
     *
     * @param chatId to extract user and animal name
     * @param report to extract report ID and text
     * @return representation of report to send to volunteer
     */
    private String prepareReportForVolunteer(Update update, Long chatId, Report report) {
        Users user = userRepository.findUserByChatId(chatId);
        StringBuilder sb = new StringBuilder();
        sb.append("\nДата: ").append(report.getDateTime().toString());
        sb.append("\nОпекун: ").append(user.getName());
        sb.append("\nЖивотное: ").append(user.getAnimal().getName());
        sb.append("\nИдентификатор отчета: ").append(report.getReportId());
        sb.append("\nОтчет: ").append(update.message().caption());
        return sb.toString();
    }

    /**
     * Method prepares starting inline keyboard
     *
     * @return <code>InlineKeyboardMarkup</code>
     */
    private static InlineKeyboardMarkup prepareStartingInlineKeyBoard() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.addRow(new InlineKeyboardButton("ℹ\uFE0F Информация").callbackData("/info"), new InlineKeyboardButton("\uD83D\uDC36 Животные").callbackData("/animals"));
        keyboardMarkup.addRow(new InlineKeyboardButton("\uD83D\uDCD5 Правила").callbackData("/rules"), new InlineKeyboardButton("\u2753 Позвать волонтера").callbackData("/help"));
        keyboardMarkup.addRow(new InlineKeyboardButton("\uD83D\uDD8B Принять контакты").callbackData("/save_user"), new InlineKeyboardButton("\uD83D\uDDD2 Сдать отчет").callbackData("/report"));
        return keyboardMarkup;
    }

    /**
     * Method prepares inline keyboard with information about shelter
     *
     * @return <code>InlineKeyboardMarkup</code>
     */
    private static InlineKeyboardMarkup prepareInfoInlineKeyBoard() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.addRow(new InlineKeyboardButton("\uD83D\uDD50 График, адрес").callbackData("/schedule"), new InlineKeyboardButton("\uD83D\uDCD1 Контакты").callbackData("/contacts"));
        keyboardMarkup.addRow(new InlineKeyboardButton("\uD83E\uDDBA Техника безопасности").callbackData("/save"));
        return keyboardMarkup;
    }

    /**
     * the method prepares the built-in keyboard with a decision on refusal,
     * approval, extension of the probation period
     *
     * @return <code>InlineKeyboardMarkup</code>
     */
    private static InlineKeyboardMarkup adoptionDecision(){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.addRow(new InlineKeyboardButton("Одобрить \uD83D\uDC4D").callbackData("/decision_for_confirmation"), new InlineKeyboardButton("Отказать \uD83D\uDED1").callbackData("/decision_to_refuse"));
        keyboardMarkup.addRow(new InlineKeyboardButton("Продлить срок \u23F2").callbackData("/decision_to_extend_probation_period"));
       return keyboardMarkup;
    }

    /**
     * this method prepares an integrated keyboard with two options for extending the probation period
     *
     * @return <code>InlineKeyboardMarkup</code>
     */
    private static InlineKeyboardMarkup extendProbationPeriod(){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.addRow(new InlineKeyboardButton("На 14 дней").callbackData("/extend_by_14_days"), new InlineKeyboardButton("На 30 дней").callbackData("/extend_by_30_days"));
        return keyboardMarkup;
    }

    /**
     * Method prepares inline keyboard with information about documents
     *
     * @return <code>InlineKeyboardMarkup</code>
     */
    private static InlineKeyboardMarkup prepareRulesInlineKeyBoard() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.addRow(new InlineKeyboardButton("\uD83D\uDCC4 Документы").callbackData("/docs"), new InlineKeyboardButton("\uD83D\uDD16 Рекомендации").callbackData("/recommends"));
        keyboardMarkup.addRow(new InlineKeyboardButton("\u2753 Возможные причины для отказа").callbackData("/reject"));
        return keyboardMarkup;
    }

    /**
     * Method prepares inline keyboard to choose cats or dogs to look at
     *
     * @return <code>InlineKeyboardMarkup</code>
     */
    private static InlineKeyboardMarkup prepareAnimalsInlineKeyBoard() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.addRow(new InlineKeyboardButton("\uD83D\uDC31 Кошки").callbackData("/cats"), new InlineKeyboardButton("\uD83D\uDC36 Собаки").callbackData("/dogs"));
        return keyboardMarkup;
    }

    /**
     * Method prepares inline keyboard to choose the animal to be attached
     *
     * @return <code>InlineKeyboardMarkup</code>
     */
    private static InlineKeyboardMarkup prepareAnimaChosenInlineKeyboard() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.addRow(new InlineKeyboardButton("\uD83D\uDE3B Хочу позаботиться!").callbackData("/take_care"));
        return keyboardMarkup;
    }
    /**
     * The method provides a keyboard in case of an error saving a contact
     *
     * @return <code>InlineKeyboardMarkup</code>
     */
    private static InlineKeyboardMarkup ConflictSaveUserInlineKeyboard(){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.addRow(new InlineKeyboardButton("\uD83D\uDD8B Принять контакты").callbackData("/save_user"));
        return keyboardMarkup;
    }

    /**
     * Method prepares inline keyboard for volunteer to accept or decline user report
     *
     * @return <code>InlineKeyboardMarkup</code>
     */
    private static InlineKeyboardMarkup prepareVolunteerInlineKeyboard() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.addRow(new InlineKeyboardButton("\uD83C\uDD97 Принять отчет").callbackData("/accept_report"), new InlineKeyboardButton("\u274E На доработку").callbackData("/decline_report"));
        return keyboardMarkup;
    }

    /**
     * Method returns to the starting menu
     *
     * @return <code>InlineKeyboardMarkup</code>
     */
    private static InlineKeyboardMarkup StepBackStartingInlineKeyboard() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.addRow(new InlineKeyboardButton("\uD83D\uDD19 Назад").callbackData("/start"));
        return keyboardMarkup;
    }

    /**
     * The method provides a keyboard in case of an error, a volunteer call
     *
     * @return <code>InlineKeyboardMarkup</code>
     */
    private static InlineKeyboardMarkup ConflictHelpVolunteerInlineKeyboard(){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.addRow(new InlineKeyboardButton("\u2753 Позвать волонтера").callbackData("/help"));
        return keyboardMarkup;
    }

    /**
     * The method provides a keyboard in case of an error when submitting a report
     *
     * @return <code>InlineKeyboardMarkup</code>
     */
    private static InlineKeyboardMarkup ConflictReportInlineKeyboard(){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.addRow(new InlineKeyboardButton("\uD83D\uDDD2 Сдать отчет").callbackData("/report"));
        return keyboardMarkup;
    }

    /**
     * Method to get chat ID depending on the type of message (callback, edited message or common message)
     *
     * @return <code>Long</code>
     * @throws NullPointerException If no ID was extracted
     */
    private Long extractChatId(Update update) {
        try {
            if (!(update.callbackQuery() == null)) {
                return update.callbackQuery().message().chat().id();
            } else if (!(update.editedMessage() == null)) {
                return update.editedMessage().chat().id();
            } else {
                return update.message().chat().id();
            }
        } catch (NullPointerException e) {
            logger.error("Chat Id is null");
            throw new RuntimeException();
        }
    }

    /**
     * Method to get text of the message depending on the type of message (callback, edited message or common message)
     *
     * @return <code>String</code>
     * @throws NullPointerException If no message text was extracted
     */
    @Nullable
    private String extractMessage(Update update, Long chatId) {
        if (update.callbackQuery() != null) {
            return update.callbackQuery().data();
        } else if (update.editedMessage() != null) {
            return update.editedMessage().text();
        } else if (update.message() != null && update.message().caption() != null) {
            return update.message().caption();
        } else if (update.message() != null){
            return update.message().text();
        } else {
            return null;
        }
    }
}
