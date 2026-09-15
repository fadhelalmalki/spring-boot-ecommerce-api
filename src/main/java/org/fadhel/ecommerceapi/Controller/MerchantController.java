package org.fadhel.ecommerceapi.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fadhel.ecommerceapi.Api.ApiResponse;
import org.fadhel.ecommerceapi.Model.Merchant;
import org.fadhel.ecommerceapi.Service.MerchantService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {
    
    private final MerchantService merchantService;

    // to get all merchants
    @GetMapping("/get")
    public ResponseEntity<?> getMerchants() {

        ArrayList<Merchant> merchants = merchantService.getMerchants();

        return ResponseEntity.status(200).body(merchants);
    }

    // to add a merchant
    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@RequestBody @Valid Merchant merchant, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        boolean isAdded = merchantService.addMerchant(merchant);

        if(isAdded == false) {
            return ResponseEntity.status(400).body(new ApiResponse("Merchant ID is taken"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Merchant added successfully"));
    }

    // to update a merchant
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable String id, @RequestBody @Valid Merchant merchant, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        boolean isUpdated = merchantService.updateMerchant(id, merchant);

        if (isUpdated == false) {
            return ResponseEntity.status(400).body(new ApiResponse("No merchant found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Merchant updated successfully"));
    }

    // to delete a merchant
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id) {

        boolean isDeleted = merchantService.deleteMerchant(id);

        if (isDeleted == false) {
            return ResponseEntity.status(400).body(new ApiResponse("No merchant found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Merchant deleted successfully"));
    }

    // to get a merchant by id
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getMerchantById(@PathVariable String id){

        Merchant merchant = merchantService.getMerchantById(id);

        if(merchant == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
        }

        return ResponseEntity.status(200).body(merchant);
    }
    
}
