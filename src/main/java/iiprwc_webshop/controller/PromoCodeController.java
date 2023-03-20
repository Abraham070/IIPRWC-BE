package iiprwc_webshop.controller;

import iiprwc_webshop.DAO.PromoCodeDAO;
import iiprwc_webshop.exceptions.RecordNotFoundException;
import iiprwc_webshop.model.database.PromoCode;
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
@RequestMapping("/api/v1/promocodes")
public class PromoCodeController {


    private final PromoCodeDAO promoCodeDAO;

    public PromoCodeController(PromoCodeDAO promoCodeDAO) {
        this.promoCodeDAO = promoCodeDAO;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<PromoCode>> getPromoCodes(){
        return ResponseEntity.ok().body(promoCodeDAO.getAll());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PromoCode> createPromoCode(@RequestBody PromoCode promoCode){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(promoCodeDAO.save(promoCode));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PromoCode> getPromoCodeById(@AuthenticationPrincipal User user, @PathVariable("id") String id){
        Optional<PromoCode> optionalPromoCode = promoCodeDAO.getById(id);

        if(optionalPromoCode.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(optionalPromoCode.get());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PromoCode> updatePromoCode(@PathVariable String id, @RequestBody PromoCode promoCode) {
        try {
            return ResponseEntity.ok().body(promoCodeDAO.update(id, promoCode));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PromoCode> deletePromoCode(@PathVariable String id) {
        try {
            promoCodeDAO.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> editPromoCode(@PathVariable("id") String id, @RequestBody Map<Object, Object> promoCodeToUpdate) {

        PromoCode promoCode = promoCodeDAO.getById(id).orElseThrow(()->new RecordNotFoundException("PromoCode with id: " + id + " not found"));

        try {
            return ResponseEntity.ok(promoCodeDAO.save(ReflectionService.patchEntity(promoCode, promoCodeToUpdate, PromoCode.class)));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Invalid data", List.of()));
        }

    }

}
