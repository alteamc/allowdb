package land.altea.allowdb.config.exception;

public final class BadConfigException extends Exception {
    public BadConfigException(Throwable cause) {
        super(cause);
    }

    public BadConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}
