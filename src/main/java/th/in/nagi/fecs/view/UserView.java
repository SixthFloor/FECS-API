package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class UserView {
	
	public interface Summary extends Personal,Role {
	
	}

	public interface Personal extends Standardized {
		
	}

	public interface Location {
	
	}

	public interface PaymentInformation {
	
	}
	
	public interface Role extends RoleView.Personal {
		
	}
	
	public interface All extends Personal, Location, PaymentInformation{
		
	}
}
