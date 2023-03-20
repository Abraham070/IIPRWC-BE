package iiprwc_webshop.controller;

import iiprwc_webshop.DAO.OrderDAO;
import iiprwc_webshop.exceptions.RecordNotFoundException;
import iiprwc_webshop.model.database.Order;
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
@RequestMapping("/api/v1/orders")
public class OrderController {


    private final OrderDAO orderDAO;

    public OrderController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(){
        return ResponseEntity.ok().body(orderDAO.getAll());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('USER')")
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(orderDAO.save(order));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('USER')")
    public ResponseEntity<Order> getOrderById(@AuthenticationPrincipal User user, @PathVariable("id") String id){
        Optional<Order> optionalOrder = orderDAO.getById(id);

        if(optionalOrder.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(optionalOrder.get());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Order> updateOrder(@PathVariable String id, @RequestBody Order order) {
        try {
            return ResponseEntity.ok().body(orderDAO.update(id, order));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Order> deleteOrder(@PathVariable String id) {
        try {
            orderDAO.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> editOrder(@PathVariable("id") String id, @RequestBody Map<Object, Object> orderToUpdate) {

        Order order = orderDAO.getById(id).orElseThrow(()->new RecordNotFoundException("Order with id: " + id + " not found"));

        try {
            return ResponseEntity.ok(orderDAO.save(ReflectionService.patchEntity(order, orderToUpdate, Order.class)));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Invalid data", List.of()));
        }

    }

}
