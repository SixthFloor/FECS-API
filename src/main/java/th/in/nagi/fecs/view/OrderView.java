package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class OrderView {
	public interface Personal extends Standardized, User, Cart {
	}
	
	public interface User extends UserView.Personal{
		
	}
	
	public interface Cart extends CartView.Personal {
		
	}
}
