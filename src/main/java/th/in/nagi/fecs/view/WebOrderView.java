package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class WebOrderView {

	public interface Personal extends Standardized, User, WebLineProduct {
	}

	public interface User extends UserView.Personal {

	}
	
	public interface WebLineProduct extends WebLineProductView.Personal {
		
	}
}
