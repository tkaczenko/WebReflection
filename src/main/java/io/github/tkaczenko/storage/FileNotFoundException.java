package io.github.tkaczenko.storage;

/**
 * Created by tkaczenko on 14.02.17.
 */
public class FileNotFoundException extends StorageException {
    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
