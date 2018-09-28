package core.exceptions;

public class FailedAuthorizationException extends Exception implements VezhBankException {

    private String detail;

    public FailedAuthorizationException(String message, String detail) {
        super(message);
        this.detail = detail;
    }

    public FailedAuthorizationException(String message) {
        super(message);
        this.detail = message;
    }

    @Override
    public String getDetail() {
        return detail;
    }
}
