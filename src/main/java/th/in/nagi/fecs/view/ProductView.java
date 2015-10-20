package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;
import th.in.nagi.fecs.view.SubCategoryView.ElementalCategory;

public class ProductView{
	public interface Summary extends Standardized, Personal, ElementalImage, ElementalSubCategory{}
	public interface Personal extends Standardized{}
	public interface ElementalImage extends Standardized, th.in.nagi.fecs.view.ProductImageView.Personal{}
	public interface ElementalSubCategory extends Standardized, th.in.nagi.fecs.view.SubCategoryView.Personal, ElementalCategory{}
}
