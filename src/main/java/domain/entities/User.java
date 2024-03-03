package domain.entities;

import domain.vo.UserId;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;

@Getter
public class User implements Comparable<User> {

    private final UserId id;
    private String name;
    private String email;
    private final LocalDateTime created;
    private LocalDateTime lastUpdated;

    public User(UserId id, String name, String email, LocalDateTime created) {
        if (id == null || name == null || name.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Id, name, and email must not be null or empty");
        }
        this.id = id;
        this.name = name;
        this.email = email;
        this.created = created;
        this.lastUpdated = created;
    }

    public void updateNameAndEmail(String name, String email) {
        if (name == null || name.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Name and email must not be null or empty");
        }
        this.name = name;
        this.email = email;
        this.lastUpdated = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", created=" + created +
                ", lastUpdated=" + lastUpdated +
                '}';
    }

    @Override
    public int compareTo(User other) {
        if (other == null) {
            return 1; // this instance is greater than null
        }
        return this.id.getId().compareTo(other.id.getId());
    }

    // Other methods can be added if necessary
}