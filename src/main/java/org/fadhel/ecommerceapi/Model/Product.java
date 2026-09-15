package org.fadhel.ecommerceapi.Model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    @NotEmpty(message = "Field must not be empty")
    @Size(min=3, message="Field must be at least 3 characters")
    private String id;

    @NotEmpty(message = "Field must not be empty")
    @Size(min=4, message="Field must be at least 4 characters")
    private String name;

    @NotNull(message = "Field must not be null")
    @Positive(message ="Field must be a positive number")
    private Double price;

    @NotEmpty(message = "Field must not be empty")
    @Size(min=3, message="Field must be at least 3 characters")
    private String categoryID;
}
