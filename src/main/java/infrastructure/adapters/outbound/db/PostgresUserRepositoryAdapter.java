package infrastructure.adapters.outbound.db;

import application.ports.out.UserRepositoryPort;
import domain.entities.User;
import domain.exceptions.UserPersistenceException;
import domain.vo.UserId;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.Optional;

public class PostgresUserRepositoryAdapter implements UserRepositoryPort {

    private final EntityManager entityManager;

    public PostgresUserRepositoryAdapter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public UserId saveUser(User user) throws UserPersistenceException {
        try {
            entityManager.persist(user);
            return user.getId();
        } catch (PersistenceException e) {
            throw new UserPersistenceException("Failed to persist user with ID: " + (user != null && user.getId() != null ? user.getId().getId() : "unknown"), e);
        }
    }

    @Override
    public Optional<User> findUserById(UserId userId) throws UserPersistenceException {
        try {
            User user = entityManager.find(User.class, userId);
            return Optional.ofNullable(user);
        } catch (PersistenceException e) {
            throw new UserPersistenceException("Failed to find user by ID: " + (userId != null ? userId.getId() : "unknown"), e);
        }
    }

    @Override
    public void deleteUser(UserId userId) throws UserPersistenceException {
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        try {
            User user = entityManager.find(User.class, userId);
            if (user != null) {
                entityManager.remove(user);
            }
        } catch (PersistenceException e) {
            throw new UserPersistenceException("Failed to delete user with ID: " + userId.getId(), e);
        }
    }
}
