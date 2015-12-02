package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class WebOrderView {

	public interface Personal extends Standardized {
	}

	public interface User extends Personal, UserView.Personal {

	}
	
	public interface WebCart extends Personal, WebCartView.Personal {
		
	}
}
