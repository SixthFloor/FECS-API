package th.in.nagi.fecs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import th.in.nagi.fecs.message.ErrorMessage;
import com.fasterxml.jackson.annotation.JsonView;
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
	@JsonView(CategoryView.Personal.class)
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity showAllCategory() {
		return new ResponseEntity(categoryService.findAll(), HttpStatus.OK);
	}
	
	/**
	 * Return list of subcategory
	 * 
	 * @param categoryName
	 * @return list of subcategory
	 */
	@JsonView(SubCategoryView.Personal.class)
	@RequestMapping(value = "/subCategory/all", method = RequestMethod.GET)
	public ResponseEntity showAllSubCategory() {
		return new ResponseEntity(subCategoryService.findAll(), HttpStatus.OK);
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
	public ResponseEntity getListUsers(@RequestParam(value = "start", required = false) int start,
			@RequestParam(value = "size", required = false) int size) {
		int categoryListSize = categoryService.findAll().size();
		if (size > categoryListSize - start) {
			size = categoryListSize - start;
		}
		List<Category> category = (categoryService.findAndAscByName(start, size));
		if (category == null) {
			return new ResponseEntity(new Message("Not found category"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(category, HttpStatus.OK);
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
	public ResponseEntity getListsubCategorys(@RequestParam(value = "start", required = false) int start,
			@RequestParam(value = "size", required = false) int size) {
		int subCategoryListSize = subCategoryService.findAll().size();
		if (size > subCategoryListSize - start) {
			size = subCategoryListSize - start;
		}
		List<SubCategory> subCategory = (subCategoryService.findAndAscByName(start, size));
		if (subCategory == null) {
			return new ResponseEntity(new Message("Not found subCategory"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(subCategory, HttpStatus.OK);
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
	public ResponseEntity showProductsByCategory(@PathVariable String categoryName) {

		Category category = categoryService.findByName(categoryName);
		if (category == null) {
			return new ResponseEntity(new Message("This category name is not existed"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(category.getSubCategories(), HttpStatus.OK);
	}
	
	/**
	 * Return list of product in the category
	 * 
	 * @param categoryName
	 *            category name that want to show products inside
	 * @return list of product
	 */
	@JsonView(CategoryView.Summary.class)
	@RequestMapping(value = "/{subCategoryName}", method = RequestMethod.GET)
	public ResponseEntity showProductsBySubCategory(@PathVariable String subCategoryName) {

		SubCategory subCategory = subCategoryService.findByName(subCategoryName);
		if (subCategory == null) {
			return new ResponseEntity(new Message("This subCategory name is not existed"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(subCategory.getProducts(), HttpStatus.OK);
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
	public ResponseEntity createNewCategory(@RequestBody Category category, @RequestHeader(value = "Authorization") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
		if (categoryService.findByName(category.getName()) != null) {
			return new ResponseEntity(new Message("This category name is existed"), HttpStatus.BAD_REQUEST);
		}
		try {
			categoryService.store(category);
		} catch (Exception e) {
			return new ResponseEntity(new Message("Create category failed"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new Message("Category has created"), HttpStatus.CREATED);
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
	public ResponseEntity addSubCategory(@RequestBody SubCategory subCategory, @PathVariable String categoryName, @RequestHeader(value = "Authorization") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
		if (subCategoryService.findByName(subCategory.getName()) != null) {
			return new ResponseEntity(new Message("This subCategory name is not existed"), HttpStatus.BAD_REQUEST);
		}
		Category category = categoryService.findByName(categoryName);
		subCategory.setCategory(category);
		try {
			subCategoryService.store(subCategory);
		} catch (Exception e) {
			return new ResponseEntity(new Message("Create subCategory failed"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new Message("subCategory has created"), HttpStatus.CREATED);
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
	public ResponseEntity editCategory(@RequestBody Category category, @RequestHeader(value = "Authorization") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		Category oldCategory = categoryService.findByKey(category.getId());
		if (oldCategory == null) {
			return new ResponseEntity(new Message("This category name is not existed"), HttpStatus.BAD_REQUEST);
		}

		oldCategory.setName(category.getName());

		try {
			categoryService.update(oldCategory);
		} catch (Exception e) {
			return new ResponseEntity(new Message("Edit category failed"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new Message("Category has editted"), HttpStatus.OK);
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
	public ResponseEntity editSubCategory(@RequestBody SubCategory subCategory,
			@RequestParam(value = "newCategoryName", required = false) String categoryName, @RequestHeader(value = "Authorization") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		Category category = categoryService.findByName(categoryName);
		SubCategory oldSubCategory = subCategoryService.findByKey(subCategory.getId());
		if (oldSubCategory == null) {
			return new ResponseEntity(new Message("This subCategory is not exist"), HttpStatus.BAD_REQUEST);
		}

		oldSubCategory.setName(subCategory.getName());

		if (category != null) {
			oldSubCategory.setCategory(category);
		}
		try {
			subCategoryService.update(oldSubCategory);
		} catch (Exception e) {
			return new ResponseEntity(new Message("Create subCategory failed"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new Message("subCategory has editted"), HttpStatus.OK);
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
	public ResponseEntity deleteCategory(@RequestBody Category category, @RequestHeader(value = "Authorization") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		try {
			categoryService.removeById(category.getId());
		} catch (Exception e) {
			return new ResponseEntity(new Message("Remove category failed"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new Message("Category has removed"), HttpStatus.OK);
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
	public ResponseEntity deleteSubCategory(@RequestBody SubCategory subCategory, @RequestHeader(value = "Authorization") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		try {
			subCategoryService.removeById(subCategory.getId());
		} catch (Exception e) {
			return new ResponseEntity(new Message("Remove subCategory failed"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new Message("SubCategory has removed"), HttpStatus.OK);
	}
}
