package th.in.nagi.fecs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.message.FailureMessage;
import th.in.nagi.fecs.message.Message;
import th.in.nagi.fecs.message.SuccessMessage;
import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.SubCategory;
import th.in.nagi.fecs.service.AuthenticateService;
import th.in.nagi.fecs.service.CategoryService;
import th.in.nagi.fecs.service.FurnitureDescriptionService;
import th.in.nagi.fecs.service.SubCategoryService;
import th.in.nagi.fecs.view.CategoryView;
import th.in.nagi.fecs.view.SubCategoryView;

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
	private FurnitureDescriptionService furnitureDescriptionService;

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
     * authenticate service.
     */
    @Autowired
    private AuthenticateService authenticateService;

	/**
	 * Return list of category
	 * 
	 * @param categoryName
	 * @return list of products in the category
	 */
	@JsonView(CategoryView.Summary.class)
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Message showAllCategory() {
		return new SuccessMessage(Message.SUCCESS, categoryService.findAll());
	}
	
	/**
	 * Return list of subcategory
	 * 
	 * @param categoryName
	 * @return list of subcategory
	 */
	@JsonView(SubCategoryView.Summary.class)
	@RequestMapping(value = "/subCategory/all", method = RequestMethod.GET)
	public Message showAllSubCategory() {
		return new SuccessMessage(Message.SUCCESS, subCategoryService.findAll());
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
	@JsonView(CategoryView.Summary.class)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Message getListUsers(@RequestParam(value = "start", required = false) int start,
			@RequestParam(value = "size", required = false) int size) {
		int categoryListSize = categoryService.findAll().size();
		if (size > categoryListSize - start) {
			size = categoryListSize - start;
		}
		List<Category> category = (categoryService.findAndAscByName(start, size));
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
	@JsonView(SubCategoryView.Summary.class)
	@RequestMapping(value = "/subCategory/list", method = RequestMethod.GET)
	public Message getListsubCategorys(@RequestParam(value = "start", required = false) int start,
			@RequestParam(value = "size", required = false) int size) {
		int subCategoryListSize = subCategoryService.findAll().size();
		if (size > subCategoryListSize - start) {
			size = subCategoryListSize - start;
		}
		List<SubCategory> subCategory = (subCategoryService.findAndAscByName(start, size));
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
	@JsonView(CategoryView.Summary.class)
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
	public Message createNewCategory(@RequestBody Category category, @RequestHeader(value = "token") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new FailureMessage(Message.FAIL, "This user does not allow");
		}
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
	public Message addSubCategory(@RequestBody SubCategory subCategory, @PathVariable String categoryName, @RequestHeader(value = "token") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new FailureMessage(Message.FAIL, "This user does not allow");
		}
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
	public Message editCategory(@RequestBody Category category, @RequestHeader(value = "token") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new FailureMessage(Message.FAIL, "This user does not allow");
		}

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
			@RequestParam(value = "newCategoryName", required = false) String categoryName, @RequestHeader(value = "token") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new FailureMessage(Message.FAIL, "This user does not allow");
		}

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
	public Message deleteCategory(@RequestBody Category category, @RequestHeader(value = "token") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new FailureMessage(Message.FAIL, "This user does not allow");
		}

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
	public Message deleteSubCategory(@RequestBody SubCategory subCategory, @RequestHeader(value = "token") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new FailureMessage(Message.FAIL, "This user does not allow");
		}

		try {
			subCategoryService.removeById(subCategory.getId());
		} catch (Exception e) {
			return new FailureMessage(Message.FAIL, "Remove subCategory failed");
		}
		return new SuccessMessage(Message.SUCCESS, "subCategory" + " has removed");
	}
}
