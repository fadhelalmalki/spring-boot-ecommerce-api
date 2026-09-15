package org.fadhel.ecommerceapi.Service;

import org.fadhel.ecommerceapi.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

    ArrayList<Merchant> merchants = new ArrayList<>();


    // to get all merchants
    public ArrayList<Merchant> getMerchants() {
        return merchants;
    }

    // to add a merchant
    public boolean addMerchant(Merchant merchant) {

        // to check id is not taken
        if(getMerchantById(merchant.getId()) != null) {
            return false;
        }

        merchants.add(merchant);
        return true;
    }

    // to update a merchant
    public boolean updateMerchant(String id, Merchant updatedMerchant) {

        for(int i=0;i<merchants.size();i++) {
            if(merchants.get(i).getId().equals(id)) {
                merchants.set(i,updatedMerchant);
                return true;
            }
        }
        return false;
    }

    // to delete a merchant
    public boolean deleteMerchant(String id) {

        for (Merchant merchant : merchants) {
            if (merchant.getId().equals(id)) {
                merchants.remove(merchant);
                return true;
            }
        }
        return false;
    }

    // to get a merchant by id
    public Merchant getMerchantById(String id){
        for(Merchant merchant : merchants){
            if(merchant.getId().equals(id)){
                return merchant;
            }
        }
        return null;
    }
}
