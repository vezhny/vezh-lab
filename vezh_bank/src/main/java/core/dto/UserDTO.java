package core.dto;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import core.json.UserConfig;
import core.json.UserData;
import vezh_bank.constants.UserDefault;
import vezh_bank.persistence.entity.Card;
import vezh_bank.persistence.entity.User;
import vezh_bank.persistence.entity.UserRequest;
import vezh_bank.persistence.entity.UserRole;
import vezh_bank.util.Encryptor;

import java.util.ArrayList;
import java.util.List;

public class UserDTO implements BaseDTO<User> {
    private final transient Encryptor ENCRYPTOR = new Encryptor();

    @Expose
    private int id;

    @Expose
    private String login;

    @Expose
    private String password;

    @Expose
    private UserRoleDTO role;

    @Expose
    private UserConfig config;

    @Expose
    private int attemptsToSignIn;

    @Expose
    private boolean blocked;

    @Expose
    private String lastSignIn;

    @Expose
    private UserData data;

    private transient List<CardDTO> cards;

    private transient List<UserRequestDTO> userRequests;

    private transient User user;

    public UserDTO(String login, String password, UserRole role, UserData data) {
        this.login = login;
        this.password = ENCRYPTOR.encrypt(password);
        this.role = new UserRoleDTO(role);
        this.data = data;
        this.config = new UserConfig();
        this.attemptsToSignIn = UserDefault.ATTEMPTS_TO_SIGN_IN;
        this.cards = new ArrayList<>();
        this.userRequests = new ArrayList<>();
    }

    public UserDTO(User user, UserRole role) {
        Gson gson = new Gson();
        this.id = user.getId();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.role = new UserRoleDTO(role);
        this.config = gson.fromJson(user.getConfig(), UserConfig.class);
        this.attemptsToSignIn = user.getAttemptsToSignIn();
        this.blocked = user.isBlocked();
        this.lastSignIn = user.getLastSignIn();
        this.data = gson.fromJson(user.getData(), UserData.class);
        this.cards = convertCards(user.getCards());
        this.userRequests = convertRequests(user.getUserRequests());
        this.user = user;
    }

    private List<CardDTO> convertCards(List<Card> cards) {
        List<CardDTO> cardDTOS = new ArrayList<>();
        for (Card card : cards) {
            cardDTOS.add(new CardDTO(card, this));
        }
        return cardDTOS;
    }

    private List<UserRequestDTO> convertRequests(List<UserRequest> userRequests) {
        List<UserRequestDTO> userRequestDTOS = new ArrayList<>();
        for (UserRequest userRequest : userRequests) {
            userRequestDTOS.add(new UserRequestDTO(userRequest, this));
        }
        return userRequestDTOS;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return ENCRYPTOR.decrypt(password);
    }

    public String getEncryptedPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRoleDTO getRole() {
        return role;
    }

    public void setRole(UserRoleDTO role) {
        this.role = role;
    }

    public UserConfig getConfig() {
        return config;
    }

    public void setConfig(UserConfig config) {
        this.config = config;
    }

    public int getAttemptsToSignIn() {
        return attemptsToSignIn;
    }

    public void setAttemptsToSignIn(int attemptsToSignIn) {
        this.attemptsToSignIn = attemptsToSignIn;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getLastSignIn() {
        return lastSignIn;
    }

    public void setLastSignIn(String lastSignIn) {
        this.lastSignIn = lastSignIn;
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    public List<CardDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardDTO> cards) {
        this.cards = cards;
    }

    public List<UserRequestDTO> getUserRequests() {
        return userRequests;
    }

    public void setUserRequests(List<UserRequestDTO> userRequests) {
        this.userRequests = userRequests;
    }

    @Override
    public User getEntity() {
        return user;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
