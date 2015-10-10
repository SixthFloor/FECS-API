package th.in.nagi.fecs.service;

import java.util.List;

import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.model.User;


public interface ProductService {

    Product findByKey(Integer id);

    void store(Product product);

    void update(Product product);

    List<Product> findAll();

    Product findBySerialNumber(String serialNumber);
    
    void removeBySerialNumber(String serialNumber);

//    boolean isUsernameUnique(Integer i, String username);

}
