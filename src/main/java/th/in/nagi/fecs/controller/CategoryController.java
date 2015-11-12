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

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.message.Message;
import th.in.nagi.fecs.model.Category;
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
	 * list of category with limit size
	 * 
	 * @param start
	 *            position of the list
	 * @param size
	 *            size of the list
	 * @return limit list of category
	 */
	@JsonView(CategoryView.Personal.class)
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
	 * Return list of product in the category
	 * 
	 * @param categoryName
	 *            category name that want to show products inside
	 * @return list of product
	 */
	@JsonView(SubCategoryView.Personal.class)
	@RequestMapping(value = "/{categoryName}", method = RequestMethod.GET)
	public ResponseEntity showProductsByCategory(@PathVariable String categoryName) {

		Category category = categoryService.findByName(categoryName);
		if (category == null) {
			return new ResponseEntity(new Message("This category name is not existed"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(category.getSubCategories(), HttpStatus.OK);
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
	public ResponseEntity createNewCategory(@RequestBody Category category,
			@RequestHeader(value = "token") String token) {
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
	 * Edit category
	 * 
	 * @param category
	 *            new information of category
	 * @return category if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public ResponseEntity editCategory(@RequestBody Category category, @RequestHeader(value = "token") String token) {
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
	 * Delete category
	 * 
	 * @param category
	 *            category that want to delete
	 * @return message success if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseEntity deleteCategory(@RequestBody Category category, @RequestHeader(value = "token") String token) {
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
}
