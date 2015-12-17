package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class OrderView {
	
	public interface Personal extends Summary, ShippingView.Summary {
		
	}
	
	public interface Summary extends Standardized, UserView.Personal, CartView.Personal {
		
	}
}
