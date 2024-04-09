package at.ac.uibk.estore.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {

    @NotEmpty(message = "{required.field}")
    private String username;

    @NotEmpty(message = "{required.field}")
    private String password;
}
