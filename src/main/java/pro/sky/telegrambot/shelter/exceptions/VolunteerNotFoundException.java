package pro.sky.telegrambot.shelter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VolunteerNotFoundException extends RuntimeException{
    public VolunteerNotFoundException() {
    }

    public VolunteerNotFoundException(String message) {
        super(message);
    }

    public VolunteerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public VolunteerNotFoundException(Throwable cause) {
        super(cause);
    }

    public VolunteerNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
