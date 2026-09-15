package org.fadhel.ecommerceapi.Service;

import lombok.RequiredArgsConstructor;
import org.fadhel.ecommerceapi.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {

    ArrayList<Product> products = new ArrayList<>();

    private final CategoryService categoryService;

    // to get all products
    public ArrayList<Product> getProducts() {
        return products;
    }

    // to add a product
    public boolean addProduct(Product product) {

        if(categoryService.getCategoryById(product.getCategoryID())==null){
            return false;
        }

        // to check id is not taken
        if(getProductById(product.getId()) != null){
            return false;
        }

        products.add(product);
        return true;
    }

    // to update a product
    public boolean updateProduct(String id, Product updatedProduct) {

        if(categoryService.getCategoryById(updatedProduct.getCategoryID())==null){
            return false;
        }

        for(int i=0;i<products.size();i++){
            if(products.get(i).getId().equals(id)){
                products.set(i, updatedProduct);
                return true;
            }
        }
        return false;
    }

    // to delete a product
    public boolean deleteProduct(String id) {

        for (Product product : products) {
            if (product.getId().equals(id)) {
                products.remove(product);
                return true;
            }
        }
        return false;
    }

    // to get a product by id
    public Product getProductById(String id){
        for(Product product : products){
            if(product.getId().equals(id)){
                return product;
            }
        }
        return null;
    }

    // 5 outOf 5 mandatory extra: to get all products by the price range
    public ArrayList<Product> getProductsByPriceRange(Double minPrice, Double maxPrice){

        ArrayList<Product> searchedProducts = new ArrayList<>();

        for(Product product : products){
            if(product.getPrice()>=minPrice&&product.getPrice()<=maxPrice){
                searchedProducts.add(product);
            }
        }
        return searchedProducts;
    }
}
