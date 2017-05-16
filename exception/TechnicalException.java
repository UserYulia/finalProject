package by.galkina.game.exception;

public class TechnicalException extends Exception {
    public TechnicalException() {
    }

    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }

    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException(Throwable cause) {
        super(cause);
    }
}
