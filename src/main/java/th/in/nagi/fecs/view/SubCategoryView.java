package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;
import th.in.nagi.fecs.view.FurnitureDescriptionView.ElementalImage;

public class SubCategoryView {
	public interface Personal {}
	public interface Summary extends Personal, ElementalFurnitureDescription{}
	public interface ElementalCategory extends Personal, th.in.nagi.fecs.view.CategoryView.Personal{}
	public interface ElementalFurnitureDescription extends Personal, th.in.nagi.fecs.view.FurnitureDescriptionView.Personal, ElementalImage{}
}
