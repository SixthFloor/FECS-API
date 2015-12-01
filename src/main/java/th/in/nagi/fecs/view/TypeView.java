package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;
import th.in.nagi.fecs.view.CategoryView;
import th.in.nagi.fecs.view.SubCategoryView;
import th.in.nagi.fecs.view.CatalogView;

public class TypeView {
	public interface Summary extends  Personal, Category, SubCategory, Catalogs{
	}

	public interface Personal extends Standardized{
	}
	
	public interface Category extends CategoryView.Personal, Personal{
	}
	
	public interface SubCategory extends SubCategoryView.Personal{
	}
	
	public interface Catalogs extends CatalogView.ProductDescription{
	}
	
	public interface SubCategoryAndCatalogs extends SubCategory, Catalogs{	
	}
	
	public interface SubCategoryAndCategory extends SubCategory, Category{	
	}
	
	
}
