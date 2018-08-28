package vezh_bank.persistence.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private int id;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "USER_PASSWORD")
    private String password;

    @Column(name = "ROLE_ID") // TODO: from role entity
    private int roleId;

    @Column(name = "CONFIG")
    private String config;

    @Column(name = "ATTEMPTS_TO_SIGN_IN_LEFT")
    private int attemptsToSignIn;

    @Column(name = "BLOCKED")
    private boolean blocked;

    @Column(name = "LAST_SIGN_IN_DATE")
    private Date lastSignIn;

    @Column(name = "USER_DATA")
    private String data;

    public User() {
    }

    public User(String login, String password, int roleId, String data) {
        this.login = login;
        this.password = password;
        this.roleId = roleId;
        this.data = data;
        // TODO: generate default config
        this.attemptsToSignIn = 3;
        this.blocked = false;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getConfig() {
        return config;
    }

    public int getAttemptsToSignIn() {
        return attemptsToSignIn;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public Date getLastSignIn() {
        return lastSignIn;
    }

    public String getData() {
        return data;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public void setAttemptsToSignIn(int attemptsToSignIn) {
        this.attemptsToSignIn = attemptsToSignIn;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public void setLastSignIn(Date lastSignIn) {
        this.lastSignIn = lastSignIn;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roleId=" + roleId +
                ", config='" + config + '\'' +
                ", attemptsToSignIn=" + attemptsToSignIn +
                ", blocked=" + blocked +
                ", lastSignIn=" + lastSignIn +
                ", data='" + data + '\'' +
                '}';
    }
}
