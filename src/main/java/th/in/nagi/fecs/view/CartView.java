package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;;

public class CartView {
	public interface Summary extends Personal, User, ProductDescription {
	}

	public interface Personal extends Standardized {
	}

	public interface User extends UserView.Summary {

	}

	public interface ProductDescription extends ProductDescriptionView.ElementalImage {

	}
}
