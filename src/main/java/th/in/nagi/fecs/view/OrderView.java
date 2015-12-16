package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class OrderView {
	
	public interface Personal extends Standardized, UserView.Personal, CartView.Personal, ShippingView.Personal {
		
	}
}
