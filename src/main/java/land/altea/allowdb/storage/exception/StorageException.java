package land.altea.allowdb.storage.exception;

public final class StorageException extends Exception {
    public StorageException(Throwable cause) {
        super(cause);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
