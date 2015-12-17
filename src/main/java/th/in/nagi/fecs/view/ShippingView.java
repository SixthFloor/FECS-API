package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class ShippingView {
	
	public interface Personal extends Standardized {
	
	}
	
	public interface Summary extends Personal, AddressView.Personal {
		
	}
	
	public interface All extends Summary, UserView.Summary {
		
	}
}
