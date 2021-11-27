package by.vsu.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotAllowedChangeException extends RuntimeException{
    public NotAllowedChangeException() {
    }

    public NotAllowedChangeException(String message) {
        super(message);
    }

    public NotAllowedChangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAllowedChangeException(Throwable cause) {
        super(cause);
    }

    public NotAllowedChangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
