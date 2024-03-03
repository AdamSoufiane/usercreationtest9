package application.ports.in;

import domain.vo.UserId;
import infrastructure.adapters.inbound.rest.dto.UserRegistrationRequest;

/**
 * Serves as the incoming port for the registration process, defining the contract for registering a new user.
 */
public interface UserRegistrationPort {

    /**
     * Register a new user in the system and return the user identifier.
     * @param request The user registration request data
     * @return The unique identifier for the registered user
     */
    UserId registerUser(UserRegistrationRequest request);

    // TODO: If fields and constructors are added in the future, use Lombok annotations to reduce boilerplate code
}
