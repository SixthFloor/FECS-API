package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;
import th.in.nagi.fecs.view.SubCategoryView.ElementalProduct;

public class CategoryView {
	public interface Personal extends Standardized{}
	public interface Summary extends Standardized, Personal, ElementalSubCategory{}
	public interface ElementalSubCategory extends Standardized, th.in.nagi.fecs.view.SubCategoryView.Personal, ElementalProduct{}
}
