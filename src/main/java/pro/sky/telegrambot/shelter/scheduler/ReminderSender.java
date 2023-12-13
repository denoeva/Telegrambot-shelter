package pro.sky.telegrambot.shelter.scheduler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.shelter.exceptions.VolunteerNotFoundException;
import pro.sky.telegrambot.shelter.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.shelter.model.Volunteer;
import pro.sky.telegrambot.shelter.repository.ReportRepository;
import pro.sky.telegrambot.shelter.repository.UserRepository;
import pro.sky.telegrambot.shelter.repository.VolunteerRepository;

import static pro.sky.telegrambot.shelter.model.Info.*;

@Component
public class ReminderSender {

    private static final Logger LOG = LoggerFactory.getLogger(ReminderSender.class);

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VolunteerRepository volunteerRepository;

    @Scheduled(cron = "0 0 22 * * *")
    public void sendReminder() {
        LOG.info("Searching for one day old reports");
        reportRepository.findAllChatIdsWithLastReportOneDayAgo().stream().distinct().forEach(chatId -> {
            SendMessage reminderMessage = new SendMessage(chatId, REPORT_REMINDER);
            telegramBot.execute(reminderMessage);
            LOG.info("Sending reminder on report to chat " + chatId);
        });
    }

    //    @Scheduled(fixedDelay = 5000) для отладки
    @Scheduled(cron = "0 0 21 * * *")
    public void sendCongratsToUsersPassedTestPeriod() {
        LOG.info("Searching for users with more than 30 accepted reports");
        Volunteer volunteer = volunteerRepository.findAll().stream().findAny().orElseThrow(VolunteerNotFoundException::new);

        // Отправляем запрос волонтеру на завершение испытательного срока для пользователей, у которых достаточное количество отчетов
        userRepository.findAllUserChatIDsWhichPassedTestPeriod().stream().forEach(user -> {
            SendMessage forVolunteer = new SendMessage(volunteer.getChatId(),"\uD83D\uDE4F \uD83D\uDE4F \uD83D\uDE4F \nДорогой волонтер,"
                    + "\nУ пользователя: " + user.getName() + ",\nс номером: " + user.getPhoneNumber() +"\n"
                    + END_TRIAL_PERIOD_FOR_VOLUNTEER + "отдавать питомца: " + user.getAnimal().getName()).replyMarkup(TelegramBotUpdatesListener.adoptionDecision());
            telegramBot.execute(forVolunteer);
            LOG.info("Sending acceptance request to volunteer " + volunteer.getName() + " about user " + user.getName() + " with enough reports count");
        });

        // Отправляем запрос волонтеру на завершение/продление испытательного срока для пользователей, у которых НЕ достаточное количество отчетов
        LOG.info("Searching for users with more less 30 accepted reports but expired test period");
        userRepository.findAllUserChatIDsWhichFailedTestPeriod().stream().forEach(user -> {
            SendMessage forVolunteer = new SendMessage(volunteer.getChatId(),"\uD83D\uDE4F \uD83D\uDE4F \uD83D\uDE4F \nДорогой волонтер,"
                    + "\nУ пользователя: " + user.getName() + ",\nс номером: " + user.getPhoneNumber() +"\n"
                    + END_TRIAL_PERIOD_FOR_VOLUNTEER_INSUFFICIENT_REPORTS + "отдавать питомца: " + user.getAnimal().getName()).replyMarkup(TelegramBotUpdatesListener.adoptionDecision());
            telegramBot.execute(forVolunteer);
            LOG.info("Sending acceptance request to volunteer " + volunteer.getName() + " about user " + user.getName() + " with NOT enough reports count");
        });
    }

    @Scheduled(cron = "0 30 21 * * *")
    public void sendWarningToVolunteer() {
        LOG.info("Searching for two days old reports");
        Volunteer volunteer = volunteerRepository.findAll().stream().findAny().orElseThrow(VolunteerNotFoundException::new);
        userRepository.findAllUsersWithLastReportTwoDaysAgo().stream().distinct().forEach(user -> {
            SendMessage reminderMessage = new SendMessage(volunteer.getChatId(), "\u2757 Мы не получали отчет от опекуна "
                    + user.getName() + " в течение двух дней.\nПожалуйста, свяжитесь с ним в чате " + user.getChatId()
                    +  " или по телефону " + user.getPhoneNumber());
            telegramBot.execute(reminderMessage);
            LOG.info("Sending warning to volunteer " + volunteer.getName() + " about user " + user.getName());
        });
    }


}
