package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class WebLineProductView {

	public interface Personal extends Standardized {
	}
	
	public interface ProductDescription extends Personal, ProductDescriptionView.Personal {
		
	}

}
