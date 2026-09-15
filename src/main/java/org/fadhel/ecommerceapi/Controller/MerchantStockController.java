package org.fadhel.ecommerceapi.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fadhel.ecommerceapi.Api.ApiResponse;
import org.fadhel.ecommerceapi.Model.MerchantStock;
import org.fadhel.ecommerceapi.Service.MerchantStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchantStock")
@RequiredArgsConstructor
public class MerchantStockController {
    
    private final MerchantStockService merchantStockService;

    // to get all merchantStocks
    @GetMapping("/get")
    public ResponseEntity<?> getMerchantStocks() {

        ArrayList<MerchantStock> merchantStocks = merchantStockService.getMerchantStocks();

        return ResponseEntity.status(200).body(merchantStocks);
    }

    // to add a merchantStock
    @PostMapping("/add")
    public ResponseEntity<?> addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        boolean isAdded = merchantStockService.addMerchantStock(merchantStock);

        if(isAdded == false) {
            return ResponseEntity.status(400).body(new ApiResponse("MerchantStock ID is taken or no product or merchant found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("MerchantStock added successfully"));
    }

    // to update a merchantStock
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchantStock(@PathVariable String id, @RequestBody @Valid MerchantStock merchantStock, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        boolean isUpdated = merchantStockService.updateMerchantStock(id, merchantStock);

        if (isUpdated == false) {
            return ResponseEntity.status(400).body(new ApiResponse("No merchantStock or product or merchant found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("MerchantStock updated successfully"));
    }

    // to delete a merchantStock
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchantStock(@PathVariable String id) {

        boolean isDeleted = merchantStockService.deleteMerchantStock(id);

        if (isDeleted == false) {
            return ResponseEntity.status(400).body(new ApiResponse("No merchantStock found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("MerchantStock deleted successfully"));
    }

    // to get a merchantStock by id
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getMerchantStockById(@PathVariable String id){

        MerchantStock merchantStock = merchantStockService.getMerchantStockById(id);

        if(merchantStock == null) {
            return ResponseEntity.status(400).body(new ApiResponse("MerchantStock not found"));
        }

        return ResponseEntity.status(200).body(merchantStock);
    }

    // for Q11: add more product stocks
    @PutMapping("/add-stock/{productID}/{merchantID}/{additionalAmount}")
    public ResponseEntity<?> addMoreStocks(@PathVariable String productID,
                                           @PathVariable String merchantID,
                                           @PathVariable Integer additionalAmount) {

        int response = merchantStockService.addMoreStocks(productID, merchantID, additionalAmount);

        switch (response) {
            case 0:
                return ResponseEntity.status(400).body(new ApiResponse("No product found"));
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("No merchant found"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("Additional amount can't be zero or negative or null"));
            case 3:
                return ResponseEntity.status(200).body(new ApiResponse("Additional stocks added successfully "));
            case 4:
                return ResponseEntity.status(400).body(new ApiResponse("No merchant stock found"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("Something went wrong"));
        }

    }
    
}
