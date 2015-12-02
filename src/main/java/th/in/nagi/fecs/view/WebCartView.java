package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class WebCartView {

	public interface Personal extends Standardized {
	}
	
	public interface Product extends Personal, ProductView.Personal {
		
	}
}
