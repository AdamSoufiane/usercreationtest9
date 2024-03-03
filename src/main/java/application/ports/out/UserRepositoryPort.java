package application.ports.out;

import domain.entities.User;
import domain.exceptions.UserPersistenceException;
import domain.vo.UserId;

import java.util.Optional;

public interface UserRepositoryPort {

    UserId saveUser(User user) throws UserPersistenceException;

    Optional<User> findUserById(UserId userId);

    void deleteUser(UserId userId) throws UserPersistenceException;

}