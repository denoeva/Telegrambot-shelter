package pro.sky.telegrambot.shelter.model;

import java.util.Objects;

public class User {
    private Long id;
    private String name;
    private String phoneNumber;
    private Boolean attached;
    public User(){
    }

    public User(Long id, String name, String phoneNumber, Boolean attached) {
        this.id = id;
        this.name = name;
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
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(attached, user.attached);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phoneNumber, attached);
    }
}
