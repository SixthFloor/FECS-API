package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class WebPaymentView {
	
	public interface Personal extends Standardized, WebCreditCard, Shipping {
		
	}
	
	public interface WebCreditCard extends WebCreditCardView.Personal {
		
	}
	
	public interface Shipping extends ShippingView.Personal {
		
	}
}
