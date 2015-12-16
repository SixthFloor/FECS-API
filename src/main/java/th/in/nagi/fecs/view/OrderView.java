package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class OrderView {
	
	public interface Personal extends Summary, ShippingView.Personal {
		
	}
	
	public interface Summary extends Standardized, UserView.Personal, CartView.Personal {
		
	}
}
