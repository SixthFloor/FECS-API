package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;
import th.in.nagi.fecs.view.RoleView;

public class AuthenticationView {
	public interface Summary extends Personal, Role {
	}

	public interface Personal extends Standardized {
	}
	
	public interface Role extends RoleView.Personal{
	}
}
