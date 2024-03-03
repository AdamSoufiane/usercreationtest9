package application.services;

import application.ports.out.UserRepositoryPort;
import domain.entities.User;
import domain.exceptions.UserPersistenceException;
import domain.vo.UserId;
import infrastructure.adapters.inbound.rest.dto.UserRegistrationRequest;
import infrastructure.adapters.outbound.email.EmailService;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RegistrationService {

    private final UserRepositoryPort userRepositoryPort;
    private final EmailService emailService;
    private final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    public RegistrationService(UserRepositoryPort userRepositoryPort, EmailService emailService) {
        this.userRepositoryPort = userRepositoryPort;
        this.emailService = emailService;
    }

    @Transactional
    public User registerUser(UserRegistrationRequest request) throws RegistrationException {
        if (!validateRegistrationRequest(request)) {
            logger.error("Invalid registration request");
            throw new RegistrationException("Invalid registration request");
        }

        if (!checkUserUniqueness(request.getEmail())) {
            logger.error("User with email {} already exists", request.getEmail());
            throw new RegistrationException("User with email already exists");
        }

        try {
            User newUser = new User(new UserId(), request.getName(), request.getEmail(), LocalDateTime.now(), LocalDateTime.now());
            UserId userId = userRepositoryPort.saveUser(newUser);
            newUser = userRepositoryPort.findUserById(userId).orElseThrow(() -> new UserPersistenceException("User not found after save"));
            sendConfirmationEmail(newUser);
            return newUser;
        } catch (DataIntegrityViolationException ex) {
            logger.error("Data integrity violation during registration", ex);
            throw new RegistrationException("Registration data violates constraints", ex);
        }
    }

    private boolean validateRegistrationRequest(UserRegistrationRequest request) {
        // Additional password validation logic can be implemented as needed
        return EmailValidator.getInstance().isValid(request.getEmail())
                && request.getPassword().length() >= 8;
    }

    private boolean checkUserUniqueness(String email) {
        return userRepositoryPort.findUserByEmail(email).isEmpty();
    }

    private void sendConfirmationEmail(User user) {
        try {
            emailService.sendEmail(user.getEmail(), "Welcome!", "Thank you for registering.");
        } catch (Exception ex) {
            logger.error("Failed to send confirmation email to {}", user.getEmail(), ex);
            // Implement a retry mechanism or raise an alert for failed email delivery
        }
    }
}

// TODO: Extract this to its own file in the 'application.services' package
public class RegistrationException extends RuntimeException {
    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}

// TODO: Extract this to its own file in the 'application.ports.out' package
interface UserRepositoryPort {
    // Other methods...

    Optional<User> findUserByEmail(String email);
}