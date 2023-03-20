package iiprwc_webshop.controller;

import iiprwc_webshop.DAO.CategoryDAO;
import iiprwc_webshop.exceptions.RecordNotFoundException;
import iiprwc_webshop.model.database.Category;
import iiprwc_webshop.model.database.User;
import iiprwc_webshop.model.http.ErrorResponse;
import iiprwc_webshop.services.ReflectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {


    private final CategoryDAO categoryDAO;

    public CategoryController(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Category>> getCategories(){
        return ResponseEntity.ok().body(categoryDAO.getAll());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryDAO.save(category));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Category> getCategoryById(@AuthenticationPrincipal User user, @PathVariable("id") String id){
        Optional<Category> optionalCategory = categoryDAO.getById(id);

        if(optionalCategory.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(optionalCategory.get());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Category> updateCategory(@PathVariable String id, @RequestBody Category category) {
        try {
            return ResponseEntity.ok().body(categoryDAO.update(id, category));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Category> deleteCategory(@PathVariable String id) {
        try {
            categoryDAO.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> editCategory(@PathVariable("id") String id, @RequestBody Map<Object, Object> categoryToUpdate) {

        Category category = categoryDAO.getById(id).orElseThrow(()->new RecordNotFoundException("Category with id: " + id + " not found"));

        try {
            return ResponseEntity.ok(categoryDAO.save(ReflectionService.patchEntity(category, categoryToUpdate, Category.class)));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Invalid data", List.of()));
        }

    }

}
