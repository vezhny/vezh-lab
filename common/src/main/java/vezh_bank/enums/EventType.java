package vezh_bank.enums;

public enum EventType {
    // USERS
    USER_SIGN_IN,
    USER_SIGN_OUT,
    USER_SIGN_UP,
    USER_DELETE,
    USER_UPDATE,
    USER_BLOCKED,
    USER_UNBLOCKED,

    // CARDS
    NEW_CARD,
    ACTIVATING_CARD,
    BLOCKING_CARD,
    DELETE_CARD,

    // CURRENCIES
    NEW_CURRENCY,
    UPDATE_CURRENCY,
    DELETE_CURRENCY,

    // PAYMENTS
    NEW_PAYMENT,
    UPDATE_PAYMENT,
    DELETE_PAYMENT,

    // TRANSACTIONS
    RANSACTION,

    // USER REQUESTS
    NEW_USER_REQUEST,
    UPDATE_USER_REQUEST,
    DELETE_USER_REQUEST
}
