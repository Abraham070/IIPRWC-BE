package iiprwc_webshop.DAO;

import iiprwc_webshop.model.database.PromoCode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PromoCodeDAO {
    private final PromoCodeRepository promoCodeRepository;
    public PromoCodeDAO(PromoCodeRepository promoCodeRepository) {this.promoCodeRepository = promoCodeRepository;}

    public PromoCode save(PromoCode promoCode){return this.promoCodeRepository.save(promoCode);}

    public List<PromoCode> getAll(){return this.promoCodeRepository.findAll();}

    public Optional<PromoCode> getById(String id){
        return this.promoCodeRepository.findById(id);
    }

    public PromoCode update(String id, PromoCode promoCode){
        Optional<PromoCode> promoCodeToUpdate = this.promoCodeRepository.findById(id);

        if(promoCodeToUpdate.isPresent()){
            promoCodeToUpdate.get().setName(promoCode.getName());
            return this.promoCodeRepository.save(promoCodeToUpdate.get());
        }
        return null;

    }

    public void deleteById(String id){
        this.promoCodeRepository.deleteById(id);
    }
}
