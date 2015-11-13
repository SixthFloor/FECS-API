package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;
import th.in.nagi.fecs.view.SubCategoryView.ElementalCategory;

public class FurnitureDescriptionView{
	public interface Summary extends Standardized, Personal, ElementalImage, ElementalSubCategory{}
	public interface Personal extends Standardized{}
	public interface ElementalImage extends Personal, th.in.nagi.fecs.view.FurnitureImageView.Personal{}
	public interface ElementalSubCategory extends Personal, th.in.nagi.fecs.view.SubCategoryView.Personal, ElementalCategory{}
}
