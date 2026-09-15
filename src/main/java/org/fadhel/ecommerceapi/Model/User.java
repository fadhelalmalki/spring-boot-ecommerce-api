package org.fadhel.ecommerceapi.Model;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "Field must not be empty")
    @Size(min=3, message="Field must be at least 3 characters")
    private String id;

    @NotEmpty(message = "Field must not be empty")
    @Size(min=6, message="Field must be at least 6 characters")
    private String username;

    @NotEmpty(message = "Field must not be empty")
    @Size(min=7, message="Field must be at least 7 length long")
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d).*$", message="Field must have characters and digits")
    private String password;

    @NotEmpty(message = "Field must not be empty")
    @Email(message="Field must be a valid email")
    private String email;

    @NotEmpty(message = "Field must not be empty")
    @Pattern(regexp="^(admin|customer)$", message="Field must be admin or customer only")
    private String role;

    @NotNull(message="Field must not be null")
    @Positive(message="balance must be a positive value")
    private Double balance;
}
