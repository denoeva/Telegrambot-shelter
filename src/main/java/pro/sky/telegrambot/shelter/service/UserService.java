package pro.sky.telegrambot.shelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.shelter.exceptions.UserNotFoundException;
import pro.sky.telegrambot.shelter.model.Users;
import pro.sky.telegrambot.shelter.repository.UserRepository;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pro.sky.telegrambot.shelter.model.SaveUserContacts.*;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private TelegramBot telegramBot;

    private String name = null;
    private String phone_number = null;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users saveUser(Users user) {
        return userRepository.save(user);
    }

    public void saveContactInfo(long chatId, String messageText, String userRequest) {
        if (messageText.equals(NAME)) {
            name = null;
            phone_number = null;
            sendMessageReply(chatId, messageText);
        } else if (messageText.equals(PHONE)) {
            if (validateAndSaveName(userRequest)) {
                sendMessageReply(chatId, messageText);
            } else {
                sendMessageReply(chatId, NAME_AGAIN);
            }
        } else if (messageText.equals(SAVE)) {
            Users user = new Users();
            Users prevUser = findUserByChatId(chatId);
            if (prevUser == null) {
                user.setChatId(chatId);
                } else {
                    user = prevUser;
                }
            user.setName(name);
            user.setPhoneNumber(phone_number);
            saveUser(user);
            sendMessage(chatId, messageText);
            }
    }

    private void sendMessageReply(long chatId, String messageText) {
        SendMessage sendMess = new SendMessage(chatId, messageText);
        sendMess.replyMarkup(new ForceReply());
        telegramBot.execute(sendMess);
    }
    private void sendMessage(long chatId, String messageText) {
        SendMessage sendMess = new SendMessage(chatId, messageText);
        telegramBot.execute(sendMess);
    }

    private boolean validateAndSaveName(String name) {
        String validateName = name.trim();
        Pattern pattern = Pattern.compile("[A-Za-zА-Яа-я\\s]{2,30}");
        Matcher matcher = pattern.matcher(validateName);

        if (matcher.matches()) {
            this.name = validateName;
            return true;
        } else {
            return false;
        }
    }
    private boolean validateAndSavePhone(String phone) {
        String validatePhone = phone.replaceAll("\\D", "");
        Pattern pattern = Pattern.compile("\\d{10,11}");
        Matcher matcher = pattern.matcher(validatePhone);

        if (matcher.matches()) {
            this.phone_number = validatePhone;
            return true;
        } else {
            return false;
        }
    }

    public Users findUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    /**
     * Method returns all requests that were received through the bot and recorded in the user table in the database.
     * @return Collection
     */
    public Collection<Users> getAllOrders() {
        return userRepository.findAll();
    }

    /**
     * Method to find User by chat id.
     * @param chatId
     * @return User
     */
    public Users findUserByChatId(Long chatId) {
        return userRepository.findUserByChatId(chatId);
    }
}
