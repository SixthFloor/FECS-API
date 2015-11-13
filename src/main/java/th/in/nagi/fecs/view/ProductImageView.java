package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class ProductImageView {
	public interface Summary extends Personal, ElementalProduct {
	}

	public interface Personal extends Standardized {
	}

	public interface ElementalProduct extends Personal {
	}
}
