package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class ProductView {

	public interface Personal extends Standardized, ProductDescription {
	
	}

	public interface ProductDescription extends ProductDescriptionView.Personal {
	
	}
}
