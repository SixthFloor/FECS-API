package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;
import th.in.nagi.fecs.view.ProductView.ElementalImage;

public class SubCategoryView {
	public interface Personal extends Standardized{}
	public interface Summary extends Standardized, Personal, ElementalProduct{}
	public interface ElementalCategory extends Standardized, th.in.nagi.fecs.view.CategoryView.Personal{}
	public interface ElementalProduct extends Standardized, th.in.nagi.fecs.view.ProductView.Personal, ElementalImage{}
}
