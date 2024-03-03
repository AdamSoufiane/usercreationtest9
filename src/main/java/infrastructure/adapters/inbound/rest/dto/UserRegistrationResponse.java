package infrastructure.adapters.inbound.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import domain.vo.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Objects;

@Value
@Builder
@AllArgsConstructor
public class UserRegistrationResponse {

    @NotNull
    @JsonProperty("userId")
    UserId userId;

    @NotNull
    @JsonProperty("message")
    String message;

    @NotNull
    @JsonProperty("createdTimestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime createdTimestamp;

    public UserId getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public OffsetDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserRegistrationResponse that = (UserRegistrationResponse) obj;
        return Objects.equals(userId, that.userId) && Objects.equals(message, that.message) && Objects.equals(createdTimestamp, that.createdTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, message, createdTimestamp);
    }

    @Override
    public String toString() {
        return "UserRegistrationResponse{" +
                "userId=" + userId +
                ", message='" + message + '\'' +
                ", createdTimestamp=" + createdTimestamp +
                '}';
    }
}
