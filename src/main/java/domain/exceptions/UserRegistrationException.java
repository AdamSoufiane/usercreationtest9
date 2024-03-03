package domain.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationException extends RuntimeException {

    private static final long serialVersionUID = 7818375828146090155L;
    private int errorCode;
    private long timestamp;
    private String userAction;
    private Map<String, Object> details;
    private int statusCode;

    public UserRegistrationException(String message) {
        super(message);
        this.timestamp = System.currentTimeMillis();
    }

    public UserRegistrationException(String message, Throwable cause) {
        super(message, cause);
        this.timestamp = System.currentTimeMillis();
    }

}