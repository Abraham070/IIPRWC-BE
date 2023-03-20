package iiprwc_webshop.DAO;

import iiprwc_webshop.model.database.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryDAO {
    private final CategoryRepository categoryRepository;
    public CategoryDAO(CategoryRepository categoryRepository) {this.categoryRepository = categoryRepository;}

    public Category save(Category category){return this.categoryRepository.save(category);}

    public List<Category> getAll(){return this.categoryRepository.findAll();}

    public Optional<Category> getById(String id){
        return this.categoryRepository.findById(id);
    }

    public Category update(String id, Category category){
        Optional<Category> categoryToUpdate = this.categoryRepository.findById(id);

        if(categoryToUpdate.isPresent()){
            categoryToUpdate.get().setName(category.getName());
            return this.categoryRepository.save(categoryToUpdate.get());
        }
        return null;

    }

    public void deleteById(String id){
        this.categoryRepository.deleteById(id);
    }
}
