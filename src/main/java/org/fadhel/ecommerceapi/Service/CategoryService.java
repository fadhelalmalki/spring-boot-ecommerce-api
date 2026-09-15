package org.fadhel.ecommerceapi.Service;

import org.fadhel.ecommerceapi.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {

    ArrayList<Category> categories = new ArrayList<>();


    // to get all categories
    public ArrayList<Category> getCategories() {
        return categories;
    }

    // to add a category
    public boolean addCategory(Category category) {

        // to check id is not taken
        if(getCategoryById(category.getId()) != null) {
            return false;
        }

        categories.add(category);
        return true;
    }

    // to update a category
    public boolean updateCategory(String id, Category updatedCategory) {

        for(int i=0;i<categories.size();i++){
            if(categories.get(i).getId().equals(id)){
                categories.set(i,updatedCategory);
                return true;
            }
        }
        return false;
    }

    // to delete a category
    public boolean deleteCategory(String id) {

        for(Category category : categories) {
            if(category.getId().equals(id)) {
                categories.remove(category);
                return true;
            }
        }
        return false;
    }

    // to get a category by id
    public Category getCategoryById(String id){
        for(Category category : categories){
            if(category.getId().equals(id)){
                return category;
            }
        }
        return null;
    }
}
