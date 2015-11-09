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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import th.in.nagi.fecs.message.ErrorMessage;
import th.in.nagi.fecs.message.Message;
import th.in.nagi.fecs.message.SuccessMessage;
import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.model.SubCategory;
import th.in.nagi.fecs.service.AuthenticateService;
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
	@ResponseBody
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Message showAllCategory() {
		return new SuccessMessage(Message.SUCCESS, categoryService.findAll(), "200");
	}
	
	/**
	 * Return list of subcategory
	 * 
	 * @param categoryName
	 * @return list of subcategory
	 */
	@ResponseBody
	@RequestMapping(value = "/subCategory/all", method = RequestMethod.GET)
	public Message showAllSubCategory() {
		return new SuccessMessage(Message.SUCCESS, subCategoryService.findAll(), "200");
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
		List<Category> category = (categoryService.findAndAscByName(start, size));
		if (category == null) {
			return new ErrorMessage(Message.ERROR, "Not found category.", "400");
		}
		return new SuccessMessage(Message.SUCCESS, category, "200");
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
		List<SubCategory> subCategory = (subCategoryService.findAndAscByName(start, size));
		if (subCategory == null) {
			return new ErrorMessage(Message.ERROR, "Not found subCategory.", "400");
		}
		return new SuccessMessage(Message.SUCCESS, subCategory, "200");
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
			return new ErrorMessage(Message.ERROR, "This category name is not existed", "400");
		}

		if (category == null) {
			return new ErrorMessage(Message.ERROR, "No products in this category", "400");
		}
		return new SuccessMessage(Message.SUCCESS, category.getProducts(), "200");
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
			return new ErrorMessage(Message.ERROR, "This user does not allow", "401");
		}
		if (categoryService.findByName(category.getName()) != null) {
			return new ErrorMessage(Message.ERROR, "This category name is existed", "400");
		}
		try {
			categoryService.store(category);
		} catch (Exception e) {
			return new ErrorMessage(Message.ERROR, "Create category failed", "400");
		}
		return new SuccessMessage(Message.SUCCESS, category, "200");
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
			return new ErrorMessage(Message.ERROR, "This user does not allow", "401");
		}
		if (subCategoryService.findByName(subCategory.getName()) != null) {
			return new ErrorMessage(Message.ERROR, "This subCategory name is not existed", "400");
		}
		Category category = categoryService.findByName(categoryName);
		subCategory.setCategory(category);
		try {
			subCategoryService.store(subCategory);
		} catch (Exception e) {
			return new ErrorMessage(Message.ERROR, "Create subCategory failed", "400");
		}
		return new SuccessMessage(Message.SUCCESS, subCategory, "200");
	}

	/**
	 * Edit category
	 * 
	 * @param category
	 *            new information of category
	 * @return category if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public Message editCategory(@RequestBody Category category, @RequestHeader(value = "token") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new ErrorMessage(Message.ERROR, "This user does not allow", "401");
		}

		Category oldCategory = categoryService.findByKey(category.getId());
		if (oldCategory == null) {
			return new ErrorMessage(Message.ERROR, "This category name is not existed", "400");
		}

		oldCategory.setName(category.getName());

		try {
			categoryService.update(oldCategory);
		} catch (Exception e) {
			return new ErrorMessage(Message.ERROR, "Edit category failed", "400");
		}

		return new SuccessMessage(Message.SUCCESS, category, "200");
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
	@RequestMapping(value = "/subCategory/edit", method = RequestMethod.PUT)
	public Message editSubCategory(@RequestBody SubCategory subCategory,
			@RequestParam(value = "newCategoryName", required = false) String categoryName, @RequestHeader(value = "token") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new ErrorMessage(Message.ERROR, "This user does not allow", "401");
		}

		Category category = categoryService.findByName(categoryName);
		SubCategory oldSubCategory = subCategoryService.findByKey(subCategory.getId());
		if (oldSubCategory == null) {
			return new ErrorMessage(Message.ERROR, "this subCategory is not exist", "400");
		}

		oldSubCategory.setName(subCategory.getName());

		if (category != null) {
			oldSubCategory.setCategory(category);
		}
		try {
			subCategoryService.update(oldSubCategory);
		} catch (Exception e) {
			return new ErrorMessage(Message.ERROR, "Create subCategory failed", "400");
		}
		return new SuccessMessage(Message.SUCCESS, subCategory, "200");
	}

	/**
	 * Delete category
	 * 
	 * @param category
	 *            category that want to delete
	 * @return message success if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Message deleteCategory(@RequestBody Category category, @RequestHeader(value = "token") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new ErrorMessage(Message.ERROR, "This user does not allow", "401");
		}

		try {
			categoryService.removeById(category.getId());
		} catch (Exception e) {
			return new ErrorMessage(Message.ERROR, "Remove category failed", "400");
		}
		return new SuccessMessage(Message.SUCCESS, "category" + " has removed", "200");
	}

	/**
	 * Delete subcategory
	 * 
	 * @param subCategory
	 *            subcategory that want to delete
	 * @return message success if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value = "/subCategory/delete", method = RequestMethod.DELETE)
	public Message deleteSubCategory(@RequestBody SubCategory subCategory, @RequestHeader(value = "token") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new ErrorMessage(Message.ERROR, "This user does not allow", "401");
		}

		try {
			subCategoryService.removeById(subCategory.getId());
		} catch (Exception e) {
			return new ErrorMessage(Message.ERROR, "Remove subCategory failed", "400");
		}
		return new SuccessMessage(Message.SUCCESS, "subCategory" + " has removed", "200");
	}
}
