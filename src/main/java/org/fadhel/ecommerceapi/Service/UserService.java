package org.fadhel.ecommerceapi.Service;

import lombok.RequiredArgsConstructor;
import org.fadhel.ecommerceapi.Model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {

    ArrayList<User> users = new ArrayList<>();
    final ArrayList<String> giftedCustomersWith100 = new ArrayList<>();
    ArrayList<Purchase>  purchases = new ArrayList<>();

    private final ProductService productService;
    private final MerchantService merchantService;
    private final MerchantStockService merchantStockService;

    // to get all users
    public ArrayList<User> getUsers() {
        return users;
    }

    // to add a user
    public boolean addUser(User user) {

        // to check id is not taken
        if(getUserById(user.getId()) != null) {
            return false;
        }

        users.add(user);
        return true;
    }

    // to update a user
    public boolean updateUser(String id, User updatedUser) {

        for(int i=0;i<users.size();i++) {
            if(users.get(i).getId().equals(id)) {
                users.set(i, updatedUser);
                return true;
            }
        }
        return false;
    }

    // to delete a user
    public boolean deleteUser(String id) {

        for (User user : users) {
            if (user.getId().equals(id)) {
                users.remove(user);
                return true;
            }
        }
        return false;
    }

    // to get a user by id
    public User getUserById(String id){
        for(User user : users){
            if(user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }

    // for Q12: to let user buy a product directly
    public int buyProduct(String id, String productID, String merchantID){

        User user = getUserById(id);
        Product product = productService.getProductById(productID);
        Merchant merchant = merchantService.getMerchantById(merchantID);
        MerchantStock merchantStock = merchantStockService.getMerchantStock(productID, merchantID);

        if(user == null){
            return 0;
        }
        if(product == null){
            return 1;
        }
        if(merchant == null){
            return 2;
        }
        if(merchantStock == null){
            return 3;
        }

        if(merchantStock.getStock() <= 0){
            return 4;
        }
        if(user.getBalance() < product.getPrice()){
            return 5;
        }
        merchantStock.setStock(merchantStock.getStock() - 1);
        user.setBalance(user.getBalance() - product.getPrice());
        purchases.add(new Purchase(id,productID,merchantID));

        return 6;
    }

    // 1 outOf 5 mandatory extras: to buy product with 10% discount
    public int buyProductWith10Discount(String id, String productID, String merchantID, String coupon){

        User user = getUserById(id);
        Product product = productService.getProductById(productID);
        Merchant merchant = merchantService.getMerchantById(merchantID);
        MerchantStock merchantStock = merchantStockService.getMerchantStock(productID, merchantID);


        if(user == null){
            return 0;
        }
        if(product == null){
            return 1;
        }
        if(merchant == null){
            return 2;
        }
        if(merchantStock == null){
            return 3;
        }

        if(merchantStock.getStock() <= 0){
            return 4;
        }

        Double discount10 = 10.0;
        Double discountedPrice = product.getPrice() * (1 - discount10 / 100);

        if(user.getBalance() < discountedPrice){
            return 5;
        }

        if(coupon == null){
            return 6;
        }

        if(!coupon.equals("10Discount")){
           return 7;
        }

        if(user.getBalance() < 1000){
            return 8;
        }

        merchantStock.setStock(merchantStock.getStock() - 1);
        user.setBalance(user.getBalance() -  discountedPrice);
        purchases.add(new Purchase(id,productID,merchantID));

        return 9;
    }

    // 2 outOf 5 mandatory extras: to add more balance to user
    public boolean addBalance(String id, Double additionalBalance){
        User user = getUserById(id);

        if(user == null){
            return false;
        }
        user.setBalance(user.getBalance() + additionalBalance);
        return true;
    }

    // 3 outOf 5 mandatory extras: to transfer balance between two users
    public int transferBalance(String fromID, String toID, Double transferredBalance){
        User fromUser = getUserById(fromID);
        User toUser = getUserById(toID);

        if(fromUser == null){
            return 0;
        }
        if(toUser == null){
            return 1;
        }
        if(fromUser.getBalance() < transferredBalance){
            return 2;
        }

        fromUser.setBalance(fromUser.getBalance() - transferredBalance);
        toUser.setBalance(toUser.getBalance() + transferredBalance);
        return 3;
    }

    // 4 outOf 5 mandatory extras: to refund a product
    public int refundProduct(String id, String productID, String merchantID) {
        User user = getUserById(id);
        Product product = productService.getProductById(productID);
        MerchantStock merchantStock = merchantStockService.getMerchantStock(productID, merchantID);

        if (user == null) return 0;
        if (product == null) return 1;
        if (merchantStock == null) return 2;

        Purchase purchasedProduct = null;

        for (Purchase purchase : purchases) {
            if (purchase.getUserID().equals(id)
                    && purchase.getProductID().equals(productID)
                    && purchase.getMerchantID().equals(merchantID)) {
                purchasedProduct = purchase;
                break;
            }
        }

        if (purchasedProduct == null) {
            return 3;
        }

        user.setBalance(user.getBalance() + product.getPrice());
        merchantStock.setStock(merchantStock.getStock() + 1);
        purchases.remove(purchasedProduct);

        return 4;
    }

//    public int withdrawBalance(String id, Double withdrawnBalance){
//        User user = getUserById(id);
//        if(withdrawnBalance == null || withdrawnBalance <= 0){
//            return 0;
//        }
//        if(user == null){
//            return 1;
//        }
//        if(user.getBalance() < withdrawnBalance){
//            return 2;
//        }
//        user.setBalance(user.getBalance() - withdrawnBalance);
//        return 3;
//    }


    /* 1 outOf 3 real extras: to let admin can gift customers that have balance more than or equal to 1000
    gifting them with 100 extra balance only one time
     */
    public int giftCustomersWithBalanceMoreThanOrEqualsTo1000(String adminID) {

        ArrayList<User> giftedCustomers = new ArrayList<>();

        User admin = getUserById(adminID);

        if(admin == null){
            return 0;
        }
        if(!admin.getRole().equalsIgnoreCase("admin")){
            return 1;
        }

        for (User user : users) {
            if (user.getRole().equalsIgnoreCase("customer") &&
                    user.getBalance() >= 1000 && !giftedCustomersWith100.contains(user.getId())) {

                // to add the gift 100
                user.setBalance(user.getBalance() + 100);

                // to put it in the list for only one time gift
                giftedCustomersWith100.add(user.getId());

                // the requested list
                giftedCustomers.add(user);
            }
        }
        return 2;
    }

    // 2 outOf 3 real extras: buy a product and get one free
    public int buyProductAndGetOneFree(String id, String productID, String merchantID, String promoCode){

        User user = getUserById(id);
        Product product = productService.getProductById(productID);
        Merchant merchant = merchantService.getMerchantById(merchantID);
        MerchantStock merchantStock = merchantStockService.getMerchantStock(productID, merchantID);

        if(user == null){
            return 0;
        }
        if(product == null){
            return 1;
        }
        if(merchant == null){
            return 2;
        }
        if(merchantStock == null){
            return 3;
        }
        if(promoCode == null){
            return 4;
        }
        if(!promoCode.equals("freeProduct")){
            return 5;
        }
        if(user.getBalance() < 3000){
            return 6;
        }

        // here stock must be more than or equals to 2 for the offer. so, I change it from 0 to 1
        if(merchantStock.getStock() <= 1){
            return 7;
        }
        if(user.getBalance() < product.getPrice()){
            return 8;
        }

        // here I change it from 1 to 2 to deduct from stock per the offer
        merchantStock.setStock(merchantStock.getStock() - 2);
        user.setBalance(user.getBalance() - product.getPrice());
        purchases.add(new Purchase(id,productID,merchantID));

        return 9;
    }











}
