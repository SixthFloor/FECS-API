package th.in.nagi.fecs.view;

import th.in.nagi.fecs.view.BaseView.Standardized;


public class FurnitureImageView {
	public interface Summary extends Standardized, ElementalProduct{}
	public interface Personal extends Standardized{}
	public interface ElementalProduct extends Standardized{}
}