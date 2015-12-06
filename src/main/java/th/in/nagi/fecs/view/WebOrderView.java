package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class WebOrderView {

	public interface Personal extends Standardized, User, WebLineProduct, Shipping {
	
	}

	public interface User extends UserView.Personal {

	}
	
	public interface WebLineProduct extends WebLineItemView.Personal {
		
	}
	
	public interface Shipping extends ShippingView.Personal {
		
	}
}
