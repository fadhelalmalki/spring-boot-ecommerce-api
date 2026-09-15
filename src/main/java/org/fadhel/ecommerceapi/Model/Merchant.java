package org.fadhel.ecommerceapi.Model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {

    @NotEmpty(message = "Field must not be empty")
    @Size(min=3, message="Field must be at least 3 characters")
    private String id;

    @NotEmpty(message = "Field must not be empty")
    @Size(min=4, message="Field must be at least 4 characters")
    private String name;
}
