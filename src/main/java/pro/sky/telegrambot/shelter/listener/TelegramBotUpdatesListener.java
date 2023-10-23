package pro.sky.telegrambot.shelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
/**
 * Class to process all incoming messages from Telegram
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Method to process all incoming messages from Telegram
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
                    String welcomeMessage = "Привет!\uD83D\uDC4B Это бот поддержки приюта для животных \"Три столицы\"! Здесь вы можете узнать информацию о приюте и посмотреть наших питомцев." +
                            " Если Вам нужна помощь волонтера, то нажмите \"Позвать на помощь\"";
                    SendMessage sendMessage = new SendMessage(chatId, welcomeMessage).replyMarkup(prepareInlineKeyBoard());
                    telegramBot.execute(sendMessage);
                    break;
                case "/schedule":
                    String scheduleAndAddress = "\uD83C\uDFE2 Адрес: Санкт-Петербург, пр. Большой Смоленский, д. 7-9, лит. А\n\uD83D\uDD50 Режим работы: С понедельника по пятницу, с 13:00 до 21:00";
                    SendMessage schedule = new SendMessage(chatId, scheduleAndAddress);
                    telegramBot.execute(schedule);
                    break;
                case "/info":
                    String shelterInfo = "Мы - некоммерческая организация «Приют для животных «Три столицы». C 2008 года мы спасаем бездомных кошек и собак, заботимся о них, ищем им дом. Мы содержим приют, в котором живут около 230 собак и 70 кошек." +
                            "\nПри поступлении в приют на каждое животное заводится личная карточка, куда вносятся данные о вакцинации," +
                            " стерилизации, причинах поступления в приют, данные о физическом состоянии и характере, ставится отметка с датой передачи в семью." +
                            " Все животные в приюте круглосуточно находятся под наблюдением сотрудников и ветеринарных врачей. ";
                    SendMessage info = new SendMessage(chatId, shelterInfo);
                    telegramBot.execute(info);
                    break;
                case "/contacts":
                    String contacts = "\u260E Телефон: +7 999 77 88 999\n\uD83E\uDEAAВы также можете оформить пропуск на машину по номеру: +7 999 65 36 647";
                    SendMessage contactInfo = new SendMessage(chatId, contacts);
                    telegramBot.execute(contactInfo);
                    break;
                case "/rules":
                    String rules = "\uD83D\uDD38Понимание обязательств: Прежде чем забрать животное из приюта, убедитесь, что вы полностью осознаете свои обязанности и готовы взять на себя ответственность за заботу о нем на протяжении всей его жизни.\n" +
                            "\n" +
                            "\uD83D\uDD38Исследуйте породу и особенности: Познакомьтесь с породой животного и его особенностями, чтобы быть уверенным, что оно подходит вам по характеру, потребностям и образу жизни.\n" +
                            "\n" +
                            "\uD83D\uDD38Участие в программе адопции: Приюты зачастую требуют предварительное участие в программе адопции, которая включает заполнение анкеты и обсуждение вашей способности предоставить достойный дом для животного.\n" +
                            "\n" +
                            "\uD83D\uDD38Визит в приют: Перед тем, как принять окончательное решение, посетите животное в приюте. Возможно, вам будет предоставлена возможность познакомиться с ним, погулять, поговорить с сотрудниками приюта или провести время с ним в игровой комнате. Это позволит вам получить представление о его поведении и характере.\n" +
                            "\n" +
                            "\uD83D\uDD38Вопросы и обсуждение: Задавайте вопросы сотрудникам приюта или ветеринару, связанные с здоровьем, поведением или особенностями животного. " +
                            "Обсудите его рацион питания, лечение от блох и глистов, возможные проблемы со здоровьем и ветеринарное обслуживание.\n" +
                            "\n" +
                            "\uD83D\uDD38Организуйте пробежку или прогулку: Если возможно, устройте прогулку с животным или проведите с ним время на просторе. " +
                            "Это поможет вам оценить его энергичность и социальность.\n" +
                            "\n" +
                            "\uD83D\uDD38Сопроводительное руководство: Просите сотрудников приюта поделиться информацией о прошлой жизни животного, его привычках, здоровье и особенностях. " +
                            "Это поможет вам лучше понять и настроиться на нового члена семьи.\n" +
                            "\n" +
                            "\uD83D\uDD38Семейное решение: Обсудите и согласуйте решение о взятии животного с членами своей семьи или сожителями, чтобы все были готовы к новому питомцу.\n" +
                            "\n" +
                            "\uD83D\uDD38Подготовьте дом: Прежде чем привести животное домой, убедитесь, что дом и среда в нем безопасны и подготовлены для приема нового животного. " +
                            "Установите границы и создайте место для отдыха, игр и питания.\n" +
                            "\n" +
                            "\uD83D\uDD38Постепенная адаптация: Ожидайте, что животное будет нуждаться во времени для приспособления к новому дому и семье. " +
                            "Уделите ему достаточно внимания, заботы и терпения для создания у него комфортной атмосферы и привязанности к вам.";
                    SendMessage rulesInfo = new SendMessage(chatId, rules);
                    telegramBot.execute(rulesInfo);
                    break;
                case "/docs":
                    String docs = "Список документов, необходимых для того, чтобы взять животное из приюта:\n" +
                            "\n" +
                            "1. Документ, удостоверяющий личность: Паспорт или другой идентификационный документ, удостоверяющий вашу личность и возраст.\n" +
                            "\n" +
                            "2. Заявка на адопцию: Возможно, вам придется заполнить заявку на адопцию, в которой вы предоставите свои личные данные, мотивацию для взятия питомца, предыдущий опыт владения животными и другую информацию.\n" +
                            "\n" +
                            "3. Договор адопции: Вам могут потребовать подписание договора адопции, где будут установлены условия и правила по уходу за животным, а также обязательства, которые вы берете на себя. Читайте и понимайте договор перед его подписанием.\n" +
                            "\n" +
                            "4. Справка от ветеринара: Приют может запросить справку от вашего ветеринара, подтверждающую, что вы в состоянии обеспечить заботу и надлежащее медицинское обслуживание для животного.\n" +
                            "\n" +
                            "5. Свидетельство о постоянном месте жительства: В некоторых случаях вам может потребоваться предоставление свидетельства о вашем постоянном месте жительства для подтверждения, что вы можете обеспечить достойные условия для питомца.\n" +
                            "\n" +
                            "6. Оплата адопционного сбора: В приютах обычно взимается адопционный сбор для покрытия расходов на уход, медицинское обслуживание и другие нужды животного. Уточните сумму и способы оплаты в приюте.\n" +
                            "\n" +
                            "7. Личная встреча и согласование: Возможно, вам потребуется пройти личную встречу с представителями приюта или провести обязательное согласование, чтобы убедиться, что вы чувствуете себя комфортно с животным и готовы взять его домой.\n" +
                            "\n" +
                            "Важно отметить, что эти требования могут различаться, поэтому лучше связаться с выбранным приютом и узнать о конкретных документах и процедуре, необходимых для адопции животного.";
                    SendMessage docsInfo = new SendMessage(chatId, docs);
                    telegramBot.execute(docsInfo);
                    break;
                case "/recommends":
                    String recommends = "\uD83D\uDE8C Правила перевоза животных разными видами транспорта.\n" +
                            "Для разных видов транспорта существуют свои правила перевозки животных. Проезд животных регламентируется определенными правилами, которые следует соблюдать владельцу. Мы собрали для вас правила для всех видов транспорта: наземного, метро, личного автомобиля и самолета. Не пренебрегайте ими, чтобы потом у вас не возникло проблемы с проездом животных в транспорте!\n" +
                            "\n" +
                            "\uD83D\uDC36 Как обустроить дом, если вы решили завести щенка или котенка?\n" +
                            "Успешная подготовка к появлению питомца начинается с небольшого приготовления к первым нескольким дням совместной жизни с домашним любимцем.\n" +
                            "\n" +
                            "\uD83C\uDFD8 Обустройства дома для взрослого животного. Думаете завести кошку, собаку, морскую свинку или хомячка? Существует 4 важных правила по уходу за животными, которых нужно придерживаться хозяевам:\n" +
                            "1. Регулярно наблюдайте за здоровьем и питанием питомца.\n" +
                            "2. Следите за гигиеной животного и его жилища.\n" +
                            "3. Гуляйте и играйте с любимцем.\n" +
                            "4. Обустройте питомцу собственный уголок.\n" +
                            "\n" +
                            "\uD83D\uDD4A Бывают ситуации, когда из-за врожденных особенностей, болезни или травмы, собака становится инвалидом. Может показаться, что жизнь такого питомца будет полна страданий, но это заблуждение. Если собака не испытывает болей, а хозяин готов ухаживать и помогать собаке адаптироваться к новой жизни, то она как правило и не замечет неудобств, связанных с особенностями ее здоровья. К таким животным нужен особенный подход.";
                    SendMessage recommendsInfo = new SendMessage(chatId, recommends);
                    telegramBot.execute(recommendsInfo);
                    break;
                default:
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Method prepares starting inline keyboard
     * @return <code>InlineKeyboardMarkup</code>
     */
    private static InlineKeyboardMarkup prepareInlineKeyBoard() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.addRow(new InlineKeyboardButton("ℹ\uFE0F Информация о приюте").callbackData("/info"), new InlineKeyboardButton("\uD83D\uDD50 Режим работы и адрес").callbackData("/schedule"));
        keyboardMarkup.addRow(new InlineKeyboardButton("\uD83D\uDCD1 Контактные данные").callbackData("/contacts"), new InlineKeyboardButton("\uD83E\uDDBA Техника безопасности").callbackData("/save"));
        keyboardMarkup.addRow(new InlineKeyboardButton("\uD83D\uDC36 Посмотреть животных").callbackData("/animals"), new InlineKeyboardButton("\u2753 Позвать волонтера").callbackData("/help"));
        keyboardMarkup.addRow(new InlineKeyboardButton("\uD83D\uDCD5 Правила знакомства").callbackData("/rules"), new InlineKeyboardButton("\uD83D\uDCC4 Необходимые документы").callbackData("/docs"));
        keyboardMarkup.addRow(new InlineKeyboardButton("\uD83D\uDD16 Рекомендации").callbackData("/recommends"));


        return keyboardMarkup;
    }

    /**
     * Method to get chat ID depending on the type of message (callback, edited message or common message)
     * @throws NullPointerException If no ID was extracted
     * @return <code>Long</code>
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
     * @throws NullPointerException  If no message text was extracted
     * @return <code>String</code>
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
