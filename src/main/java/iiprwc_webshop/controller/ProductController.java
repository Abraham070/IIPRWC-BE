package iiprwc_webshop.controller;

import iiprwc_webshop.DAO.ProductDAO;
import iiprwc_webshop.model.database.Product;
import iiprwc_webshop.model.database.User;
import iiprwc_webshop.model.http.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductDAO productDAO;

    public ProductController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(){
        return ResponseEntity.ok().body(productDAO.getAll());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(productDAO.save(product));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@AuthenticationPrincipal User user, @PathVariable("id") String id){
        Optional<Product> optionalProduct = productDAO.getById(id);

        if(optionalProduct.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(optionalProduct.get());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        try {
            return ResponseEntity.ok().body(productDAO.update(id, product));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Product> deleteProduct(@PathVariable String id) {
        try {
            productDAO.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> editProduct(@PathVariable("id") String id, @RequestBody Product productToUpdate) {

        productToUpdate.setId(id);

        try {
            return ResponseEntity.ok(productDAO.save(productToUpdate));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Invalid data", List.of()));
        }

    }

}
