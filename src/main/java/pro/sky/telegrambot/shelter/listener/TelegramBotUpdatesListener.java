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

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

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
                    String shelterInfo = "Мы - некоммерческая организация <Приют для животных <Три столицы>>. C 2008 года мы спасаем бездомных кошек и собак, заботимся о них, ищем им дом. Мы содержим приют, в котором живут около 230 собак и 70 кошек." +
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
        keyboardMarkup.addRow(new InlineKeyboardButton("ℹ Информация о приюте").callbackData("/info"), new InlineKeyboardButton("\uD83D\uDD50 Режим работы и адрес").callbackData("/schedule"));
        keyboardMarkup.addRow(new InlineKeyboardButton("\uD83D\uDCD1 Контактные данные").callbackData("/contacts"), new InlineKeyboardButton("\uD83E\uDDBA Техника безопасности").callbackData("/save"));
        keyboardMarkup.addRow(new InlineKeyboardButton("\uD83D\uDC36 Посмотреть животных").callbackData("/animals"), new InlineKeyboardButton("\u2753 Позвать на помощь").callbackData("/help"));


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
