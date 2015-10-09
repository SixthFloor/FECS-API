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
        User entity = userRepository.findByKey(user.getId());
        if (entity != null) {
            entity.setFirstName(user.getFirstName());
            entity.setLastName(user.getLastName());
            entity.setJoiningDate(user.getJoiningDate());
            entity.setUsername(user.getUsername());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removeByUsername(String username) {
        userRepository.removeByUsername(username);
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
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isUsernameUnique(Integer id, String username) {
        User user = findByUsername(username);
        return (user == null || ((id != null) && (user.getId() == id)));
    }
}
