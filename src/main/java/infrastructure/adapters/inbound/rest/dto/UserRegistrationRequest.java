package infrastructure.adapters.inbound.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.validation.UniqueEmail;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Encapsulates the incoming user registration data in a transfer object.
 * Provides a data structure for the request payload for user registration.
 */
@Getter
@Setter
public class UserRegistrationRequest {

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    @NotEmpty
    @Email
    @UniqueEmail
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 30)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}