package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class ProductDescriptionView {
	public interface Summary extends Personal, ElementalImage {
	}

	public interface Personal extends Standardized {
	}

	public interface ElementalImage extends Personal, th.in.nagi.fecs.view.ProductImageView.Personal {
	}

}
