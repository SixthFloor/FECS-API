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
import th.in.nagi.fecs.model.SubCategory;
import th.in.nagi.fecs.service.AuthenticateService;
import th.in.nagi.fecs.service.CategoryService;
import th.in.nagi.fecs.service.ProductDescriptionService;
import th.in.nagi.fecs.service.SubCategoryService;
import th.in.nagi.fecs.view.CategoryView;
import th.in.nagi.fecs.view.ProductDescriptionView;
import th.in.nagi.fecs.view.SubCategoryView;

/**
 * Controller for category
 * 
 * @author Nara Surawit
 *
 */
@RestController
@RequestMapping("/api/subCategory")
public class SubCategoryController extends BaseController {

	/**
	 * Service of product
	 */
	@Autowired
	private ProductDescriptionService productDescriptionService;

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
	 * Return list of subcategory
	 * 
	 * @param categoryName
	 * @return list of subcategory
	 */
	@JsonView(SubCategoryView.Personal.class)
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity showAllSubCategory() {
		return new ResponseEntity(subCategoryService.findAll(), HttpStatus.OK);
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
	@JsonView(SubCategoryView.Personal.class)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
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
	@JsonView(ProductDescriptionView.ElementalImage.class)
	@RequestMapping(value = "/{subCategoryName}", method = RequestMethod.GET)
	public ResponseEntity showProductsBySubCategory(@PathVariable String subCategoryName) {
		SubCategory subCategory = subCategoryService.findByName(subCategoryName);
		if (subCategory == null) {
			return new ResponseEntity(new Message("This subCategory name is not existed"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(subCategory.getProductDescriptions(), HttpStatus.OK);
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
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ResponseEntity addSubCategory(@RequestBody SubCategory subCategory, @RequestParam String categoryName,
			@RequestHeader(value = "Authorization") String token) {
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
	 * Edit subcategory
	 * 
	 * @param subCategory
	 *            new information of subcategory
	 * @param categoryName
	 *            new category of subcategory
	 * @return category if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public ResponseEntity editSubCategory(@RequestBody SubCategory subCategory,
			@RequestParam(value = "newCategoryName", required = false) String categoryName,
			@RequestHeader(value = "Authorization") String token) {
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
	 * Delete subcategory
	 * 
	 * @param subCategory
	 *            subcategory that want to delete
	 * @return message success if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseEntity deleteSubCategory(@RequestBody SubCategory subCategory,
			@RequestHeader(value = "Authorization") String token) {
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
