package pro.sky.telegrambot.shelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.shelter.exceptions.AnimalNotFoundException;
import pro.sky.telegrambot.shelter.exceptions.PhotoNotFoundException;
import pro.sky.telegrambot.shelter.model.Animal;
import pro.sky.telegrambot.shelter.model.Photo;
import pro.sky.telegrambot.shelter.model.Users;
import pro.sky.telegrambot.shelter.repository.AnimalRepository;
import pro.sky.telegrambot.shelter.repository.PhotoRepository;
import pro.sky.telegrambot.shelter.repository.UserRepository;
import pro.sky.telegrambot.shelter.service.UserService;


import javax.annotation.PostConstruct;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static pro.sky.telegrambot.shelter.model.Info.*;
import static pro.sky.telegrambot.shelter.model.SaveUserContacts.*;

/**
 * Class to process all incoming messages from Telegram
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    public TelegramBotUpdatesListener(UserService userService, TelegramBot telegramBot) {
        this.userService = userService;
        this.telegramBot = telegramBot;
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
            switch (message) {
                case "/start":
                    SendMessage sendMessage = new SendMessage(chatId, WELCOME_MESSAGE).replyMarkup(prepareStartingInlineKeyBoard());
                    telegramBot.execute(sendMessage);
                    break;
                case "/schedule":
                    SendMessage schedule = new SendMessage(chatId, SCHEDULE_AND_ADDRESS);
                    telegramBot.execute(schedule);
                    break;
                case "/info":
                    SendMessage info = new SendMessage(chatId, SHELTER_INFO).replyMarkup(prepareInfoInlineKeyBoard());
                    telegramBot.execute(info);
                    break;
                case "/contacts":
                    SendMessage contactInfo = new SendMessage(chatId, CONTACTS);
                    telegramBot.execute(contactInfo);
                    break;
                case "/rules":
                    SendMessage rulesInfo = new SendMessage(chatId, RULES).replyMarkup(prepareRulesInlineKeyBoard());
                    telegramBot.execute(rulesInfo);
                    break;
                case "/docs":
                    SendMessage docsInfo = new SendMessage(chatId, DOCS);
                    telegramBot.execute(docsInfo);
                    break;
                case "/recommends":
                    SendMessage recommendsInfo = new SendMessage(chatId, RECOMMENDATIONS);
                    telegramBot.execute(recommendsInfo);
                    break;
                case "/save":
                    SendMessage safetyInstructions = new SendMessage(chatId, SAVE_INSTRUCTIONS);
                    telegramBot.execute(safetyInstructions);
                    break;
                case "/reject":
                    SendMessage reasonsForRejections = new SendMessage(chatId, REASONS_FOR_REJECTION);
                    telegramBot.execute(reasonsForRejections);
                    break;
                case "/tipsFromDogHandler":
                    SendMessage tipsFromDogHandlerInfo = new SendMessage(chatId, TIPS_FOR_DOG_HANDLER);
                    telegramBot.execute(tipsFromDogHandlerInfo);
                    break;
                case "/recommendationsForProvenDogHandlers":
                    SendMessage recommendationsForProvenDogHandlersInfo = new SendMessage(chatId, RECS_FOR_PROVEN_DOG_HANDLER);
                    telegramBot.execute(recommendationsForProvenDogHandlersInfo);
                    break;
                case "/reasonsForRefusal":
                    SendMessage reasonsForRefusingTakeDogFromShelter = new SendMessage(chatId, REASONS_FOR_REFUSAL);
                    telegramBot.execute(reasonsForRefusingTakeDogFromShelter);
                    break;
                case "/animals":
                    SendMessage animals = new SendMessage(chatId, ANIMALS).replyMarkup(prepareAnimalsInlineKeyBoard());
                    telegramBot.execute(animals);
                    break;
                case "/cats":
                    animalRepository.findAnimalsByAttachedFalse().stream().filter(animal -> animal.getTypeOfAnimal().equals(Animal.TypeOfAnimal.CAT)).forEach(
                            animal -> {
                                Photo animalPhoto = photoRepository.findFirstByAnimal(animal).orElseThrow(PhotoNotFoundException::new);
                                SendPhoto photo = new SendPhoto(chatId, animalPhoto.getData()).caption(prepareAnimalForBot(animal)).replyMarkup(prepareAnimaChosenInlineKeyboard());
                                telegramBot.execute(photo);
                            }
                    );
                    break;
                case "/dogs":
                    animalRepository.findAnimalsByAttachedFalse().stream().filter(animal -> animal.getTypeOfAnimal().equals(Animal.TypeOfAnimal.DOG)).forEach(
                            animal -> {
                                Photo animalPhoto = photoRepository.findFirstByAnimal(animal).orElseThrow(PhotoNotFoundException::new);
                                SendPhoto photo = new SendPhoto(chatId, animalPhoto.getData()).caption(prepareAnimalForBot(animal)).replyMarkup(prepareAnimaChosenInlineKeyboard());
                                telegramBot.execute(photo);
                            }
                    );
                    break;
                case "/save_user":
                    SendMessage userName = new SendMessage(chatId, NAME);
                    telegramBot.execute(userName);
                    if (update.message() != null) {
                        String userRequest = update.message().text();
                        String replyMessage = update.message().replyToMessage().text();
                        if (update.message().replyToMessage() != null &&
                                !update.message().replyToMessage().text().isEmpty()) {
                            if (replyMessage.equals(NAME) || replyMessage.equals(NAME_AGAIN)) {
                                userService.saveContactInfo(chatId, NAME, userRequest);
                            }
                        }
                        SendMessage userPhone = new SendMessage(chatId, PHONE);
                        telegramBot.execute(userPhone);
                        if (update.message() != null) {
                            if (update.message().replyToMessage() != null &&
                                    !update.message().replyToMessage().text().isEmpty()) {
                                if (replyMessage.equals(PHONE) || replyMessage.equals(PHONE_AGAIN)) {
                                    userService.saveContactInfo(chatId, PHONE, userRequest);
                                }
                            }
                        }
                        userService.saveContactInfo(chatId, SAVE, userRequest);
                    }
                    break;
                case "/report":
                    SendMessage report = new SendMessage(chatId, REPORT_FORM);
                    telegramBot.execute(report);
                    break;
                case "/take_care":
                    String animalName = update.callbackQuery().message().caption().lines().filter(line -> line.startsWith("Имя")).map(line -> StringUtils.removeStart(line, "Имя: ")).findFirst().orElseThrow(AnimalNotFoundException::new);
                    Animal animalToAttach = animalRepository.findAnimalByName(animalName);
                    Users userToAttach = userRepository.findUserByChatId(chatId);
                    animalToAttach.setAttached(true);
                    animalToAttach.setUser(userToAttach);
                    userToAttach.setAnimal(animalToAttach);
                    animalRepository.save(animalToAttach);
                    userRepository.save(userToAttach);
                    SendMessage attached = new SendMessage(chatId, INFO_AFTER_ATTACHMENT);
                    telegramBot.execute(attached);
                    break;
                case "/help":
                    SendMessage help = new SendMessage(chatId, HELP);
                    telegramBot.execute(help);
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
    private String extractMessage(Update update, Long chatId) {
        try {
            if (!(update.callbackQuery() == null)) {
                return update.callbackQuery().data();
            } else if (!(update.editedMessage() == null)) {
                return update.editedMessage().text();
            } else {
                return update.message().text();
            }
        } catch (NullPointerException e) {
            logger.error("Message body is null");
            throw new RuntimeException();
        }
    }

}
