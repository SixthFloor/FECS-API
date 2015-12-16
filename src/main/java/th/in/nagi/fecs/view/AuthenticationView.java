package th.in.nagi.fecs.view;

import java.nio.file.attribute.UserDefinedFileAttributeView;

import th.in.nagi.fecs.view.BaseView.Standardized;
import th.in.nagi.fecs.view.RoleView;

public class AuthenticationView {
	public interface Summary extends Personal, User, Role {
	}

	public interface Personal extends Standardized {
	}
	
	public interface Role extends RoleView.Personal{
	}
	
	public interface User extends UserView.AllInformation{
		
	}
}
