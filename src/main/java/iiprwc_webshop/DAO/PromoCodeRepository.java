package iiprwc_webshop.DAO;

import iiprwc_webshop.model.database.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromoCodeRepository extends JpaRepository<PromoCode, String> {
}
