package iiprwc_webshop.DAO;

import iiprwc_webshop.model.database.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoleDAO {

    RoleRepository roleRepository;
    public RoleDAO(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAll(){
        return this.roleRepository.findAll();
    }

    public Role getById(String id){
        return this.roleRepository.findById(id).get();
    }

    public Optional<Role> getByName(String name){
        return this.roleRepository.findByName(name);
    }

    public void deleteById(String id){
        this.roleRepository.deleteById(id);
    }

}
