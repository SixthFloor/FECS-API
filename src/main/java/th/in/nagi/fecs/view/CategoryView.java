package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;
import th.in.nagi.fecs.view.SubCategoryView.ElementalFurnitureDescription;

public class CategoryView {
	public interface Personal{}
	public interface Summary extends Personal, ElementalSubCategory{}
	public interface ElementalSubCategory extends Personal, th.in.nagi.fecs.view.SubCategoryView.Personal, ElementalFurnitureDescription{}
}
