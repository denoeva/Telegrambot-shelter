package pro.sky.telegrambot.shelter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/** Class to store information about users
 * @version $Revision: 1 $
 */
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long chatId;
    private String phoneNumber;
    private Boolean attached;
    public Users(){
    }
    public Users(Long id, String name, Long chatId, String phoneNumber, Boolean attached) {
        this.id = id;
        this.name = name;
        this.chatId = chatId;
        this.phoneNumber = phoneNumber;
        this.attached = attached;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getAttached() {
        return attached;
    }

    public void setAttached(Boolean attached) {
        this.attached = attached;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users user = (Users) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(chatId, user.chatId) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(attached, user.attached);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, chatId, phoneNumber, attached);
    }
}
