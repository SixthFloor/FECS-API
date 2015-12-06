package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class OrderView {
	
	public interface Personal extends Standardized, User, Cart, Shipping {
		
	}
	
	public interface User extends UserView.Personal {
		
	}
	
	public interface Cart extends CartView.Personal {
		
	}
	
	public interface Shipping extends ShippingView.Personal {
		
	}
}
