package iiprwc_webshop.DAO;

import iiprwc_webshop.model.database.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductDAO {
    private final ProductRepository productRepository;
    public ProductDAO(ProductRepository productRepository) {this.productRepository = productRepository;}

    public Product save(Product product){
        return this.productRepository.save(product);}

    public List<Product> getAll(){return this.productRepository.findAll();}

    public Optional<Product> getById(String id){
        return this.productRepository.findById(id);
    }

    public Product update(String id, Product product){
        Optional<Product> productToUpdate = this.productRepository.findById(id);
        if(productToUpdate.isPresent()){
            productToUpdate.get().setCategory(product.getCategory());
            return this.productRepository.save(productToUpdate.get());
        }
        return null;

    }

    public void deleteById(String id){
        this.productRepository.deleteById(id);
    }
}
