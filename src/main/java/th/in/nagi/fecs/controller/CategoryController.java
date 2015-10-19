package th.in.nagi.fecs.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import th.in.nagi.fecs.message.ErrorMessage;
import th.in.nagi.fecs.message.FailureMessage;
import th.in.nagi.fecs.message.Message;
import th.in.nagi.fecs.message.SuccessMessage;
import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.model.SubCategory;
import th.in.nagi.fecs.service.CategoryService;
import th.in.nagi.fecs.service.ProductService;
import th.in.nagi.fecs.service.SubCategoryService;

/**
 * Controller for category
 * 
 * @author Nara Surawit
 *
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController extends BaseController {

	/**
	 * Service of product
	 */
	@Autowired
	private ProductService productService;

	/**
	 * Service of category
	 */
	@Autowired
	private CategoryService categoryService;

	/**
	 * Service of subcategory
	 */
	@Autowired
	private SubCategoryService subCategoryService;

	/**
	 * Return list of products in the category
	 * 
	 * @param categoryName
	 * @return list of products in the category
	 */
	@ResponseBody
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Message showAllCategory() {
		return new SuccessMessage(Message.SUCCESS, categoryService.findAll());
	}

	/**
	 * list of category with limit size
	 * 
	 * @param start
	 *            position of the list
	 * @param size
	 *            size of the list
	 * @return limit list of category
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Message getListUsers(@RequestParam(value = "start", required = false) int start,
			@RequestParam(value = "size", required = false) int size) {
		int categoryListSize = categoryService.findAll().size();
		if (size > categoryListSize - start) {
			size = categoryListSize - start;
		}
		Set<Category> category = new HashSet<Category>(categoryService.findAndAscByName(start, size));
		if (category == null) {
			return new FailureMessage(Message.FAIL, "Not found category.");
		}
		return new SuccessMessage(Message.SUCCESS, category);
	}
	
	/**
	 * list of subCategory with limit size
	 * 
	 * @param start
	 *            position of the list
	 * @param size
	 *            size of the list
	 * @return limit list of subCategory
	 */
	@ResponseBody
	@RequestMapping(value = "/subCategory/list", method = RequestMethod.GET)
	public Message getListsubCategorys(@RequestParam(value = "start", required = false) int start,
			@RequestParam(value = "size", required = false) int size) {
		int subCategoryListSize = subCategoryService.findAll().size();
		if (size > subCategoryListSize - start) {
			size = subCategoryListSize - start;
		}
		Set<SubCategory> subCategory = new HashSet<SubCategory>(subCategoryService.findAndAscByName(start, size));
		if (subCategory == null) {
			return new FailureMessage(Message.FAIL, "Not found subCategory.");
		}
		return new SuccessMessage(Message.SUCCESS, subCategory);
	}
	
	/**
	 * Return list of product in the category
	 * 
	 * @param categoryName
	 *            category name that want to show products inside
	 * @return list of product
	 */
	@ResponseBody
	@RequestMapping(value = "/{categoryName}", method = RequestMethod.GET)
	public Message showProductsByCategory(@PathVariable String categoryName) {

		Category category = categoryService.findByName(categoryName);
		if (categoryService.findByName(categoryName) == null) {
			return new FailureMessage(Message.FAIL, "This category name is not existed");
		}

		if (category == null) {
			return new FailureMessage(Message.FAIL, "No products in this category");
		}
		return new SuccessMessage(Message.SUCCESS, category.getProducts());
	}

	/**
	 * Create and add new category to database
	 * 
	 * @param category
	 *            new category
	 * @return category if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public Message createNewCategory(@RequestBody Category category) {
		if (categoryService.findByName(category.getName()) != null) {
			return new FailureMessage(Message.FAIL, "This category name is existed");
		}
		try {
			categoryService.store(category);
		} catch (Exception e) {
			return new FailureMessage(Message.FAIL, "Create category failed");
		}
		return new SuccessMessage(Message.SUCCESS, category);
	}

	/**
	 * Create and add new category to database
	 * 
	 * @param subCategory
	 *            new subcategory
	 * @param categoryName
	 *            category of subcategory.
	 * @return subCategoyr if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value = "{categoryName}/subCategory/new", method = RequestMethod.POST)
	public Message addSubCategory(@RequestBody SubCategory subCategory, @PathVariable String categoryName) {
		if (subCategoryService.findByName(subCategory.getName()) != null) {
			return new FailureMessage(Message.FAIL, "This subCategory name is existed");
		}
		Category category = categoryService.findByName(categoryName);
		subCategory.setCategory(category);
		try {
			subCategoryService.store(subCategory);
		} catch (Exception e) {
			return new FailureMessage(Message.FAIL, "Create subCategory failed");
		}
		return new SuccessMessage(Message.SUCCESS, subCategory);
	}

	/**
	 * Edit category
	 * 
	 * @param category
	 *            new information of category
	 * @return category if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public Message editCategory(@RequestBody Category category) {

		Category oldCategory = categoryService.findByKey(category.getId());
		if (oldCategory == null) {
			return new FailureMessage(Message.FAIL, "This category name is not existed");
		}

		oldCategory.setName(category.getName());

		try {
			categoryService.update(oldCategory);
		} catch (Exception e) {
			return new FailureMessage(Message.FAIL, "Edit category failed");
		}

		return new SuccessMessage(Message.SUCCESS, category);
	}

	/**
	 * Edit subcategory
	 * 
	 * @param subCategory
	 *            new information of subcategory
	 * @param categoryName
	 *            new category of subcategory
	 * @return category if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value = "/subCategory/edit", method = RequestMethod.POST)
	public Message editSubCategory(@RequestBody SubCategory subCategory,
			@RequestParam(value = "newCategoryName", required = false) String categoryName) {

		Category category = categoryService.findByName(categoryName);
		SubCategory oldSubCategory = subCategoryService.findByKey(subCategory.getId());
		if (oldSubCategory == null) {
			return new FailureMessage(Message.FAIL, "this subCategory is not exist");
		}

		oldSubCategory.setName(subCategory.getName());

		if (category != null) {
			oldSubCategory.setCategory(category);
		}
		try {
			subCategoryService.update(oldSubCategory);
		} catch (Exception e) {
			return new FailureMessage(Message.FAIL, "Create subCategory failed");
		}
		return new SuccessMessage(Message.SUCCESS, subCategory);
	}

	/**
	 * Delete category
	 * 
	 * @param category
	 *            category that want to delete
	 * @return message success if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Message deleteCategory(@RequestBody Category category) {

		try {
			categoryService.removeById(category.getId());
		} catch (Exception e) {
			return new FailureMessage(Message.FAIL, "Remove category failed");
		}
		return new SuccessMessage(Message.SUCCESS, "category" + " has removed");
	}

	/**
	 * Delete subcategory
	 * 
	 * @param subCategory
	 *            subcategory that want to delete
	 * @return message success if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value = "/subCategory/delete", method = RequestMethod.POST)
	public Message deleteSubCategory(@RequestBody SubCategory subCategory) {

		try {
			subCategoryService.removeById(subCategory.getId());
		} catch (Exception e) {
			return new FailureMessage(Message.FAIL, "Remove subCategory failed");
		}
		return new SuccessMessage(Message.SUCCESS, "subCategory" + " has removed");
	}
}
