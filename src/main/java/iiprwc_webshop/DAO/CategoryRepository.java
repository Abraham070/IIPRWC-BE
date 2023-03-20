package iiprwc_webshop.DAO;

import iiprwc_webshop.model.database.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
