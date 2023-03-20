package iiprwc_webshop.DAO;

import iiprwc_webshop.model.database.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}
