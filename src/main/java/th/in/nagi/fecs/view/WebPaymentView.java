package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class WebPaymentView {
	
	public interface Personal extends Standardized, WebCreditCardView.Personal, ShippingView.Personal {
		
	}
}