package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.repository.UserRepository;

/**
 * 
 * User Service
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByKey(Integer id) {
        return userRepository.findByKey(id);
    }

    public void store(User user) {
        userRepository.store(user);
    }

    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends. 
     */
    /**
     * {@inheritDoc}
     */
    public void update(User user) {
        User entity = userRepository.findByEmail(user.getEmail());
        if (entity != null) {
        	if(user.getFirstName()!= null){
        		entity.setFirstName(user.getFirstName());
        	}
        	if(user.getLastName() != null){
        		entity.setLastName(user.getLastName());
        	}
            entity.setJoiningDate(user.getJoiningDate());
            entity.setAddress1(user.getAddress1());
            entity.setAddress2(user.getAddress2());
            entity.setCard_name(user.getCard_name());
            entity.setCardCVV(user.getCardCVV());
            entity.setEmail(user.getEmail());
            entity.setExpirationDate(user.getExpirationDate());
            entity.setPassword(user.getPassword());
            entity.setProvince(user.getProvince());
            entity.setZipcode(user.getZipcode());
            entity.setTelephone_number(user.getTelephone_number());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removeByEmail(String email) {
        userRepository.removeByEmail(email);
    }

    /**
     * {@inheritDoc}
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isEmailUnique(Integer id, String email) {
        User user = findByEmail(email);
        return (user == null || ((id != null) && (user.getId() == id)));
    }
}
