package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class WebOrderView {

	public interface Personal extends Standardized, UserView.Personal, WebLineItemView.Personal, ShippingView.Summary {

	}
}
