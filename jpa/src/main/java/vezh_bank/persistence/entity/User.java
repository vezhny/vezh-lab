package vezh_bank.persistence.entity;


import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private UserRole role;

    @Column(name = "CONFIG")
    private String config;

    @Column(name = "ATTEMPTS_TO_SIGN_IN_LEFT")
    private int attemptsToSignIn;

    @Column(name = "BLOCKED")
    private boolean blocked;

    @Column(name = "LAST_SIGN_IN_DATE")
    private String lastSignIn;

    @Column(name = "USER_DATA")
    private String data;

    @OneToMany(mappedBy = "holder", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Card> cards;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<UserRequest> userRequests;

    public User() {
    }

    public User(String login, String password, UserRole role, String data, String config, int attemptsToSignIn) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.data = data;
        this.config = config;
        this.attemptsToSignIn = attemptsToSignIn;
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

    public String getRole() {
        return role.getName();
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

    public String getLastSignIn() {
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

    public void setLastSignIn(String lastSignIn) {
        this.lastSignIn = lastSignIn;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public List<UserRequest> getUserRequests() {
        return new ArrayList<>(userRequests);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role.getName() +
                ", config='" + config + '\'' +
                ", attemptsToSignIn=" + attemptsToSignIn +
                ", blocked=" + blocked +
                ", lastSignIn=" + lastSignIn +
                ", data='" + data + '\'' +
                '}';
    }
}
