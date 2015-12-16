package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class AuthenticationView {
	
	public interface Summary extends Personal, User, Role {
	}

	public interface Personal extends Standardized {
	}

	public interface Role extends RoleView.Personal {
	}

	public interface User extends UserView.All {

	}
}
