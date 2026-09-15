package org.fadhel.ecommerceapi.Model;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty(message = "Field must not be empty")
    @Size(min=3, message="Field must be at least 3 characters")
    private String id;

    @NotEmpty(message = "Field must not be empty")
    @Size(min=3, message="Field must be at least 3 characters")
    private String productID;

    @NotEmpty(message = "Field must not be empty")
    @Size(min=3, message="Field must be at least 3 characters")
    private String merchantID;

    @NotNull(message="Field must not be null")
    @Min(value=11, message="Field must be at least 11 stock at start")
    private Integer stock;
}
