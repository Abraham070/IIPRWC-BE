package iiprwc_webshop.DAO;

import iiprwc_webshop.model.database.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
