package core.exceptions;

public class BadRequestException extends Exception implements VezhBankException{
    private String detail;

    public BadRequestException(String message, String detail) {
        super(message);
        this.detail = detail;
    }

    public BadRequestException(String message) {
        super(message);
        this.detail = message;
    }

    @Override
    public String getDetail() {
        return detail;
    }
}
