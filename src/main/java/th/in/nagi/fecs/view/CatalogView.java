package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;
import th.in.nagi.fecs.view.TypeView;
import th.in.nagi.fecs.view.SubCategoryView;
import th.in.nagi.fecs.view.CategoryView;;

public class CatalogView {
	public interface Summary
			extends Personal, ProductDescription, Type {
	}

	public interface Personal extends Standardized {
	}
	
	public interface Type extends Personal, TypeView.SubCategoryAndCategory  {
	}
	
	public interface ProductDescription extends Personal, ProductDescriptionView.Personal {
	}
	
	public interface Catalog extends Personal, TypeView.Summary, ProductDescription{
	}
}
