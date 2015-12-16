package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;
import th.in.nagi.fecs.view.UserView.AllInformation;

public class ShippingView {
	
	public interface Personal extends Standardized {
	
	}
	
	public interface View extends Standardized, Personal, AllInformation{
		
	}

}
