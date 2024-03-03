package infrastructure.adapters.inbound;

import application.ports.in.RegistrationServicePort;
import application.ports.in.UserRegistrationPort;
import domain.vo.UserId;
import infrastructure.adapters.inbound.rest.dto.UserRegistrationRequest;
import infrastructure.adapters.inbound.rest.dto.UserRegistrationResponse;
import infrastructure.adapters.inbound.rest.exception.UserRegistrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegistrationAdapter implements UserRegistrationPort {

    private final RegistrationServicePort registrationService;

    @Override
    public UserRegistrationResponse registerUser(UserRegistrationRequest request) throws UserRegistrationException {
        try {
            UserId userId = registrationService.registerUser(request);
            return new UserRegistrationResponse(userId.getValue());
        } catch (Exception e) {
            throw new UserRegistrationException("Registration failed", e);
        }
    }
}

// TODO: Extract the UserRegistrationResponse class to its own file in the package infrastructure.adapters.inbound.rest.dto
class UserRegistrationResponse {
    private final String userId;

    public UserRegistrationResponse(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}

// TODO: Extract the UserRegistrationException class to its own file in the package infrastructure.adapters.inbound.rest.exception
class UserRegistrationException extends Exception {
    public UserRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}