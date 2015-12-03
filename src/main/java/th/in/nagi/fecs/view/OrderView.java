package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class OrderView {
	public interface Personal extends Standardized {
	}
	
	public interface User extends Standardized, UserView.Personal {
		
	}
	
	public interface Cart extends Standardized, CartView.Personal {
		
	}
}
