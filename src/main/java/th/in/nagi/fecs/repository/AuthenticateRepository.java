package th.in.nagi.fecs.repository;

import java.util.List;

import javax.persistence.criteria.Order;

import th.in.nagi.fecs.model.Authenticate;
import th.in.nagi.fecs.model.Product;

public interface AuthenticateRepository extends Repository<Authenticate, Integer>{
	
	public Authenticate findByToken(String token);
	
	public Authenticate findByUsername(String username);
	
	public void removeByToken(String token);
	
}
