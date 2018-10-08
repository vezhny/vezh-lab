package core.exceptions;

public class ServerErrorException extends Exception implements VezhBankException {
    private String detail;

    public ServerErrorException(String message, String detail) {
        super(message);
        this.detail = detail;
    }

    public ServerErrorException(String message) {
        super(message);
        this.detail = message;
    }

    @Override
    public String getDetail() {
        return detail;
    }
}
