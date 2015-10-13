package th.in.nagi.fecs.repository;

import java.util.List;

import javax.persistence.criteria.Order;

import th.in.nagi.fecs.model.Authenticate;
import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.model.User;

public interface AuthenticateRepository extends Repository<Authenticate, Integer>{
	
	public Authenticate findByToken(String token);
	
	public Authenticate findByUser(User username);
	
	public void removeByToken(String token);
	
}
