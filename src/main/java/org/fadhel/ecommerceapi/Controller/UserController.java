package org.fadhel.ecommerceapi.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fadhel.ecommerceapi.Api.ApiResponse;
import org.fadhel.ecommerceapi.Model.User;
import org.fadhel.ecommerceapi.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // to get all users
    @GetMapping("/get")
    public ResponseEntity<?> getUsers() {

        ArrayList<User> users = userService.getUsers();

        return ResponseEntity.status(200).body(users);
    }

    // to add a user
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        boolean isAdded = userService.addUser(user);

        if(isAdded == false) {
            return ResponseEntity.status(400).body(new ApiResponse("User ID is taken"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("User added successfully"));
    }

    // to update a user
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody @Valid User user, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        boolean isUpdated = userService.updateUser(id, user);

        if (isUpdated == false) {
            return ResponseEntity.status(400).body(new ApiResponse("No user found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
    }

    // to delete a user
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {

        boolean isDeleted = userService.deleteUser(id);

        if (isDeleted == false) {
            return ResponseEntity.status(400).body(new ApiResponse("No user found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully"));
    }

    // to get a user by id
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id){

        User user = userService.getUserById(id);

        if(user == null) {
            return ResponseEntity.status(400).body(new ApiResponse("User not found"));
        }

        return ResponseEntity.status(200).body(user);
    }

    // for Q12: to let user buy a product directly
    @PostMapping("/buy-product/{id}/{productID}/{merchantID}")
    public ResponseEntity<?> buyProduct(@PathVariable String id, @PathVariable String productID, @PathVariable String merchantID) {

        int response = userService.buyProduct(id, productID, merchantID);

        switch (response) {
            case 0:
                return ResponseEntity.status(400).body(new ApiResponse("No user found"));
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("No product found"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("No merchant found"));
            case 3:
                return ResponseEntity.status(400).body(new ApiResponse("No merchant stock found"));
            case 4:
                return ResponseEntity.status(400).body(new ApiResponse("No Stock found"));
            case 5:
                return ResponseEntity.status(400).body(new ApiResponse("Not enough balance"));
            case 6:
                return ResponseEntity.status(200).body(new ApiResponse("Product bought successfully"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("Something went wrong"));
        }
    }

    // 1 outOf 5 mandatory extras: to buy product with 10% discount
    @PostMapping("/buy-product-discount/{id}/{productID}/{merchantID}/{coupon}")
    public ResponseEntity<?> buyProductWith10Discount(@PathVariable String id,
                                                      @PathVariable String productID,
                                                      @PathVariable String merchantID,
                                                      @PathVariable String coupon) {

        int response = userService.buyProductWith10Discount(id, productID, merchantID, coupon);

        switch (response) {
            case 0:
                return ResponseEntity.status(400).body(new ApiResponse("No user found"));
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("No product found"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("No merchant found"));
            case 3:
                return ResponseEntity.status(400).body(new ApiResponse("No merchant stock found"));
            case 4:
                return ResponseEntity.status(400).body(new ApiResponse("No Stock found"));
            case 5:
                return ResponseEntity.status(400).body(new ApiResponse("Not enough balance"));
            case 6:
                return ResponseEntity.status(400).body(new ApiResponse("This coupon is not correct"));
            case 7:
                return ResponseEntity.status(200).body(new ApiResponse("Product bought successfully"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("Something went wrong"));
        }
    }

    // 2 outOf 5 mandatory extras: to add more balance to user
    @PutMapping("/add-balance/{id}/{additionalBalance}")
    public ResponseEntity<?> addBalance(@PathVariable String id,@PathVariable Double additionalBalance){

        boolean isAdded = userService.addBalance(id, additionalBalance);
        if(isAdded == false) {
            return ResponseEntity.status(400).body(new ApiResponse("No user found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Additional balance added successfully, new balance: "
                + userService.getUserById(id).getBalance()));
    }


    // 3 outOf 5 mandatory extras: to transfer balance between two users
    @PutMapping("/transfer-balance/{fromID}/{toID}/{transferredBalance}")
    public ResponseEntity<?> transferBalance(@PathVariable String fromID,@PathVariable String toID,
                                             @PathVariable Double transferredBalance){

        int response = userService.transferBalance(fromID, toID, transferredBalance);

        switch (response) {
            case 0:
                return ResponseEntity.status(400).body(new ApiResponse("User not found"));
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("No sufficient balance found"));
            case 2:
                return ResponseEntity.status(200).body(new ApiResponse("Money transferred successfully"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("Something went wrong"));
        }
    }

    // 4 outOf 5 mandatory extras: to refund a product
    @PutMapping("/refund-product/{id}/{productID}/{merchantID}")
    public ResponseEntity<?> refundProduct(@PathVariable String id,
                                           @PathVariable String productID,
                                           @PathVariable String merchantID) {

        int response = userService.refundProduct(id, productID, merchantID);

        switch (response) {
            case 0:
                return ResponseEntity.status(400).body(new ApiResponse("No user found"));
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("No product found"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("No merchant stock found"));
            case 3:
                return ResponseEntity.status(200).body(new ApiResponse("Product refunded successfully"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("Something went wrong"));
        }
    }
//    @PutMapping("/withdraw-balance/{id}/{withdrawnBalance}")
//    public ResponseEntity<?> withdrawBalance(@PathVariable String id,@PathVariable Double withdrawnBalance){
//
//        int response = userService.withdrawBalance(id, withdrawnBalance);
//
//        switch (response) {
//            case 0:
//                return ResponseEntity.status(400).body(new ApiResponse("Withdrawn balance can't be null, 0 or negative"));
//            case 1:
//                return ResponseEntity.status(400).body(new ApiResponse("No user found"));
//            case 2:
//                return ResponseEntity.status(400).body(new ApiResponse("no sufficient balance found"));
//            case 3:
//                return ResponseEntity.status(200).body(new ApiResponse("Withdrawal successfully"));
//            default:
//                return ResponseEntity.status(400).body(new ApiResponse("Something went wrong"));
//        }
//    }

    /* 1 outOf 3 real extras: to get all customers that have balance more than or equal to 1000
     and gift them with 100 extra balance only one time
     */
    @PutMapping("/gift-customers")
    public ResponseEntity<?> giftCustomersWithBalanceMoreThanOrEqualsTo1000() {

        ArrayList<User> users = userService.giftCustomersWithBalanceMoreThanOrEqualsTo1000();

        if (users.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("No users found"));
        }

        return ResponseEntity.status(200).body(users);
    }

    // 2 outOf 3 real extras: buy a product and get one free
    @PostMapping("/buy-product-offer/{id}/{productID}/{merchantID}")
    public ResponseEntity<?> buyProductAndGetOneFree(@PathVariable String id, @PathVariable String productID,
                                                     @PathVariable String merchantID) {

        int response = userService.buyProductAndGetOneFree(id, productID, merchantID);

        switch (response) {
            case 0:
                return ResponseEntity.status(400).body(new ApiResponse("No user found"));
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("No product found"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("No merchant found"));
            case 3:
                return ResponseEntity.status(400).body(new ApiResponse("No merchant stock found"));
            case 4:
                return ResponseEntity.status(400).body(new ApiResponse("No Stock found"));
            case 5:
                return ResponseEntity.status(400).body(new ApiResponse("Not enough balance"));
            case 6:
                return ResponseEntity.status(200).body(new ApiResponse("Product bought successfully"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("Something went wrong"));
        }
    }








}
