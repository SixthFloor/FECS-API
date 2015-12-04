package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;

public class CartView {

	public interface Personal extends Standardized, Product {
	}

	public interface User extends UserView.Personal {
	}

	public interface Product extends ProductView.Personal {
	}
}
