package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class ProductDescriptionView {
	public interface Personal extends Standardized, ElementalImage {
	}

	public interface ElementalImage extends ProductImageView.Personal {
	}

}