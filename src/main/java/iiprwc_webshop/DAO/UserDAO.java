package iiprwc_webshop.DAO;

import iiprwc_webshop.model.database.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDAO {
    private final UserRepository userRepository;

    public UserDAO(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll(){
        return this.userRepository.findAll();
    }

    public Optional<User> getById(String id){
        return this.userRepository.findById(id);
    }

    public User save(User user){
        return this.userRepository.save(user);
    }

    public void deleteById(String id){
        this.userRepository.deleteById(id);
    }
    public Optional<User> getByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

}