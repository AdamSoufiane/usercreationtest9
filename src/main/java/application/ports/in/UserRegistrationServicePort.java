package application.ports.in;

import infrastructure.adapters.inbound.rest.dto.v1.UserRegistrationRequest;
import infrastructure.adapters.inbound.rest.dto.v1.UserRegistrationResponse;
import domain.exceptions.UserRegistrationException;

/**
 * Defines the API for the RegistrationService to interact with the outside world,
 * allowing the service to be called from different adapters.
 */
public interface UserRegistrationServicePort {

    /**
     * Publicly accessible method that registers a new user in the system and returns a response
     * with the registration details. Throws UserRegistrationException if the registration process fails
     * due to validation errors, conflicts, or other issues.
     *
     * @param request the user registration request data
     * @return the user registration response
     * @throws UserRegistrationException if there are issues during the registration process
     */
    UserRegistrationResponse registerUser(UserRegistrationRequest request) throws UserRegistrationException;

}