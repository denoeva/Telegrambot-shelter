package pro.sky.telegrambot.shelter.model;

/**
 * Class to store information about volunteers working in the shelter
 * @version $Revision: 1 $
 */
public class Volunteer {
    private Long id;
    private String name;
    private Long chatId;
    public Volunteer(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
