package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class ShippingView {
	
	public interface Personal extends Standardized {
	
	}
	
	public interface All extends Personal, UserView.Summary, OrderView.Summary {
		
	}

}
