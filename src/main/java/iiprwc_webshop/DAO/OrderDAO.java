package iiprwc_webshop.DAO;

import iiprwc_webshop.model.database.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderDAO {
    private final OrderRepository orderRepository;
    public OrderDAO(OrderRepository orderRepository) {this.orderRepository = orderRepository;}

    public Order save(Order order){return this.orderRepository.save(order);}

    public List<Order> getAll(){return this.orderRepository.findAll();}

    public Optional<Order> getById(String id){
        return this.orderRepository.findById(id);
    }

    public Order update(String id, Order order){
        Optional<Order> orderToUpdate = this.orderRepository.findById(id);

        if(orderToUpdate.isPresent()){
            orderToUpdate.get().setProducts(order.getProducts());
            return this.orderRepository.save(orderToUpdate.get());
        }
        return null;

    }

    public void deleteById(String id){
        this.orderRepository.deleteById(id);
    }
}
