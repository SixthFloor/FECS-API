package th.in.nagi.fecs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Authenticate;
import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.repository.AuthenticateRepository;
import th.in.nagi.fecs.repository.ProductRepository;


@Service("authenticateService")
@Transactional
public class AuthenticateServiceImpl implements AuthenticateService{
	
	@Autowired
    private AuthenticateRepository authenticateRepository;
	
	@Autowired
    private UserService userService;

	@Override
	public Authenticate findByKey(Integer id) {
		// TODO Auto-generated method stub
		return authenticateRepository.findByKey(id);
	}

	@Override
	public void store(Authenticate authenticate) {
		// TODO Auto-generated method stub
		authenticateRepository.store(authenticate);
	}

	@Override
	public void update(Authenticate authenticate) {
		// TODO Auto-generated method stub
//		Authenticate entity = authenticateRepository.findByKey(authenticate.getId());
//        if (entity != null) {
//            entity.setUsername(authenticate.getUsername());
//            entity.setToken(authenticate.getToken());         
//       }	
		
	}

	@Override
	public List<Authenticate> findAll() {
		// TODO Auto-generated method stub
		return authenticateRepository.findAll();
	}

	@Override
	public Authenticate findByToken(String token) {
		// TODO Auto-generated method stub
		return authenticateRepository.findByToken(token);
	}

	@Override
	public void removeByToken(String token) {
		// TODO Auto-generated method stub
		authenticateRepository.removeByToken(token);
	}

	@Override
	public List<Authenticate> findByUsername(String username) {
		User user = userService.findByUsername(username);
		return user.getAuthenticate();
		
	}

}
