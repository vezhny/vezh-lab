package core.json;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import vezh_bank.constants.UserDefault;

public class UserConfig {
    @Expose
    private int cardsOnPage;

    @Expose
    private int currenciesOnPage;

    @Expose
    private int eventsOnPage;

    @Expose
    private int paymentsOnPage;

    @Expose
    private int transactionsOnPage;

    @Expose
    private int userRequestsOnPage;

    @Expose
    private int usersOnPage;

    public UserConfig() {
        setCardsOnPage(UserDefault.ROWS_ON_PAGE);
        setCurrenciesOnPage(UserDefault.ROWS_ON_PAGE);
        setEventsOnPage(UserDefault.ROWS_ON_PAGE);
        setPaymentsOnPage(UserDefault.ROWS_ON_PAGE);
        setTransactionsOnPage(UserDefault.ROWS_ON_PAGE);
        setUserRequestsOnPage(UserDefault.ROWS_ON_PAGE);
        setUsersOnPage(UserDefault.ROWS_ON_PAGE);
    }

    public int getCardsOnPage() {
        return cardsOnPage;
    }

    public void setCardsOnPage(int cardsOnPage) {
        this.cardsOnPage = cardsOnPage;
    }

    public int getCurrenciesOnPage() {
        return currenciesOnPage;
    }

    public void setCurrenciesOnPage(int currenciesOnPage) {
        this.currenciesOnPage = currenciesOnPage;
    }

    public int getEventsOnPage() {
        return eventsOnPage;
    }

    public void setEventsOnPage(int eventsOnPage) {
        this.eventsOnPage = eventsOnPage;
    }

    public int getPaymentsOnPage() {
        return paymentsOnPage;
    }

    public void setPaymentsOnPage(int paymentsOnPage) {
        this.paymentsOnPage = paymentsOnPage;
    }

    public int getTransactionsOnPage() {
        return transactionsOnPage;
    }

    public void setTransactionsOnPage(int transactionsOnPage) {
        this.transactionsOnPage = transactionsOnPage;
    }

    public int getUserRequestsOnPage() {
        return userRequestsOnPage;
    }

    public void setUserRequestsOnPage(int userRequestsOnPage) {
        this.userRequestsOnPage = userRequestsOnPage;
    }

    public int getUsersOnPage() {
        return usersOnPage;
    }

    public void setUsersOnPage(int usersOnPage) {
        this.usersOnPage = usersOnPage;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
