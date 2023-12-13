package pro.sky.telegrambot.shelter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PhotoNotFoundException extends RuntimeException {
    public PhotoNotFoundException() {
    }

    public PhotoNotFoundException(String message) {
        super(message);
    }

    public PhotoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhotoNotFoundException(Throwable cause) {
        super(cause);
    }

    public PhotoNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
