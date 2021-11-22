package by.vsu.model;

import java.util.Date;
import java.util.Objects;

/** Store additional information about user. */
public class UserInfo {
    private Integer id;

    private String name;

    private Date birthday;

    private String phone;

    private String email;

    private String avatar;

    private User user;

    public UserInfo() {}

    public UserInfo(String name, Date birthday, String phone, String email, String avatar, User user) {
        this.name = name;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.avatar = avatar;
        this.user = user;
    }

    public UserInfo(Integer id, String name, Date birthday, String phone, String email, String avatar, User user) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.avatar = avatar;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(id, userInfo.id) && Objects.equals(name, userInfo.name) && Objects.equals(birthday, userInfo.birthday) && Objects.equals(phone, userInfo.phone) && Objects.equals(email, userInfo.email) && Objects.equals(avatar, userInfo.avatar) && Objects.equals(user, userInfo.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthday, phone, email, avatar, user);
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", user=" + user +
                '}';
    }
}
