package infrastructure.adapters.inbound.rest;

import infrastructure.adapters.inbound.UserRegistrationAdapter;
import infrastructure.adapters.inbound.rest.dto.UserRegistrationRequest;
import infrastructure.adapters.inbound.rest.dto.UserRegistrationResponse;
import infrastructure.adapters.inbound.rest.exception.UserRegistrationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
public class UserRegistrationRestController {

    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationRestController.class);

    private final UserRegistrationAdapter userRegistrationAdapter;

    public UserRegistrationRestController(UserRegistrationAdapter userRegistrationAdapter) {
        this.userRegistrationAdapter = userRegistrationAdapter;
    }

    @PostMapping("/users/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        UserRegistrationResponse response = userRegistrationAdapter.registerUser(userRegistrationRequest);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(UserRegistrationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<String> handleUserRegistrationException(UserRegistrationException e) {
        logger.error("User registration failed: ", e);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e) {
        logger.error("An error occurred during user registration: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
}

// TODO: Extract the UserRegistrationException class to its own file in the package infrastructure.adapters.inbound.rest.exception
class UserRegistrationException extends Exception {
    public UserRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}