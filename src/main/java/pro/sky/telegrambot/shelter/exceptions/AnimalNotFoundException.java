package pro.sky.telegrambot.shelter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AnimalNotFoundException extends RuntimeException{
    public AnimalNotFoundException() {
    }

    public AnimalNotFoundException(String message) {
        super(message);
    }

    public AnimalNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnimalNotFoundException(Throwable cause) {
        super(cause);
    }

    public AnimalNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
