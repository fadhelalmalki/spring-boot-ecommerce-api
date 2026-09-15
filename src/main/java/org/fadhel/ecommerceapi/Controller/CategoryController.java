package org.fadhel.ecommerceapi.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fadhel.ecommerceapi.Api.ApiResponse;
import org.fadhel.ecommerceapi.Model.Category;
import org.fadhel.ecommerceapi.Service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // to get all categories
    @GetMapping("/get")
    public ResponseEntity<?> getCategories() {

        ArrayList<Category> categories = categoryService.getCategories();

        return ResponseEntity.status(200).body(categories);
    }

    // to add a category
    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody @Valid Category category, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        boolean isAdded = categoryService.addCategory(category);

        if(isAdded == false) {
            return ResponseEntity.status(400).body(new ApiResponse("Category ID is taken"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Category added successfully"));
    }

    // to update a category
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @RequestBody @Valid Category category, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        boolean isUpdated = categoryService.updateCategory(id, category);

        if (isUpdated == false) {
            return ResponseEntity.status(400).body(new ApiResponse("No category found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Category updated successfully"));
    }

    // to delete a category
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {

        boolean isDeleted = categoryService.deleteCategory(id);

        if (isDeleted == false) {
            return ResponseEntity.status(400).body(new ApiResponse("No category found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Category deleted successfully"));
    }

    // to get a category by id
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable String id){

        Category category = categoryService.getCategoryById(id);

        if(category == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Category not found"));
        }

        return ResponseEntity.status(200).body(category);
    }

}
