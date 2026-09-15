package org.fadhel.ecommerceapi.Service;

import lombok.RequiredArgsConstructor;
import org.fadhel.ecommerceapi.Model.MerchantStock;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    private final ProductService productService;
    private final MerchantService merchantService;


    // to get all merchant stocks
    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }

    // to add a merchant stock
    public boolean addMerchantStock(MerchantStock merchantStock) {

        if(productService.getProductById(merchantStock.getProductID()) == null ||
        merchantService.getMerchantById(merchantStock.getMerchantID()) == null) {
            return false;
        }

        // to check id is not taken
        if(getMerchantStockById(merchantStock.getId()) != null) {
            return false;
        }

        merchantStocks.add(merchantStock);
        return true;
    }

    // to update a merchant stock
    public boolean updateMerchantStock(String id, MerchantStock updatedMerchantStock) {

        if (productService.getProductById(updatedMerchantStock.getProductID()) == null ||
                merchantService.getMerchantById(updatedMerchantStock.getMerchantID()) == null) {
            return false;
        }

        for(int i=0; i<merchantStocks.size(); i++) {
            if(merchantStocks.get(i).getId().equals(id)) {
                merchantStocks.set(i, updatedMerchantStock);
                return true;
            }
        }
        return false;
    }

    // to delete a merchant stock
    public boolean deleteMerchantStock(String id) {

        for (MerchantStock merchantStock : merchantStocks) {
            if (merchantStock.getId().equals(id)) {
                merchantStocks.remove(merchantStock);
                return true;
            }
        }
        return false;
    }

    // to get a merchant stock by id
    public MerchantStock getMerchantStockById(String id){
        for(MerchantStock merchantStock : merchantStocks){
            if(merchantStock.getId().equals(id)){
                return merchantStock;
            }
        }
        return null;
    }

    // for Q11: add more product stocks
    public int addMoreStocks(String productID, String merchantID, Integer additionalAmount) {
        if(productService.getProductById(productID) == null) {
            return 0;
        }
        if(merchantService.getMerchantById(merchantID) == null) {
            return 1;
        }
        if(additionalAmount <= 0 || additionalAmount == null) {
            return 2;
        }

        for(MerchantStock merchantStock : merchantStocks) {
            if(merchantStock.getProductID().equals(productID) && merchantStock.getMerchantID().equals(merchantID)) {
                merchantStock.setStock(merchantStock.getStock() + additionalAmount);
                return 3; // for 200 ok
            }
        }
        return 4;
    }

    // to get merchant stock by product id and merchant id
    public MerchantStock getMerchantStock(String productID, String merchantID) {
        for(MerchantStock merchantStock : merchantStocks) {
            if(merchantStock.getProductID().equals(productID) && merchantStock.getMerchantID().equals(merchantID)) {
                return merchantStock;
            }
        }
        return null;
    }

}
