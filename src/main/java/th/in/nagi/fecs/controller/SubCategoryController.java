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
import th.in.nagi.fecs.model.ProductDescription;
import th.in.nagi.fecs.model.SubCategory;
import th.in.nagi.fecs.service.AuthenticationService;
import th.in.nagi.fecs.service.CategoryService;
import th.in.nagi.fecs.service.SubCategoryService;
import th.in.nagi.fecs.service.TypeService;
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
	private AuthenticationService authenticationService;

	/**
	 * type service.
	 */
	@Autowired
	private TypeService typeService;

	/**
	 * Return list of subcategory
	 * 
	 * @param categoryName
	 * @return list of subcategory
	 */
	@JsonView(SubCategoryView.Personal.class)
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<?> showAllSubCategory() {
		return new ResponseEntity<List<SubCategory>>(subCategoryService.findAll(), HttpStatus.OK);
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
	public ResponseEntity<?> getListsubCategorys(@RequestParam(value = "start", required = false) int start,
			@RequestParam(value = "size", required = false) int size) {

		int subCategoryListSize = subCategoryService.findAll().size();

		if (size > subCategoryListSize - start) {
			size = subCategoryListSize - start;
		}

		List<SubCategory> subCategories = (subCategoryService.findAndAscByName(start, size));

		if (subCategories == null) {
			return new ResponseEntity<Message>(new Message("Not found subCategory"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<List<SubCategory>>(subCategories, HttpStatus.OK);
	}

	/**
	 * Return list of product in the category
	 * 
	 * @param categoryName
	 *            category name that want to show products inside
	 * @return list of product
	 */
	@JsonView(ProductDescriptionView.Personal.class)
	@RequestMapping(value = "/{subCategoryName}", method = RequestMethod.GET)
	public ResponseEntity<?> showProductsBySubCategory(@PathVariable String subCategoryName,
			@RequestParam(value = "category", required = false) String categoryName) {

		Category category = categoryService.findByName(categoryName);
		SubCategory subCategory = subCategoryService.findByName(subCategoryName);

		if (subCategory == null) {
			return new ResponseEntity<Message>(new Message("This subCategory name is not existed"),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<List<ProductDescription>>(
				typeService.findProductByCategoryAndSubCategory(category, subCategory), HttpStatus.OK);
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
	public ResponseEntity<?> addSubCategory(@RequestBody SubCategory subCategory, //@RequestParam String categoryName,
			@RequestHeader(value = "Authorization") String token) {

		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		if (subCategoryService.findByName(subCategory.getName()) != null) {
			return new ResponseEntity<Message>(new Message("This subCategory name is not existed"),
					HttpStatus.BAD_REQUEST);
		}

		try {
			subCategoryService.store(subCategory);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Create subCategory failed"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Message>(new Message("subCategory has created"), HttpStatus.CREATED);
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
	public ResponseEntity<?> editSubCategory(@RequestBody SubCategory subCategory,
			@RequestHeader(value = "Authorization") String token) {

		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		SubCategory oldSubCategory = subCategoryService.findByKey(subCategory.getId());

		if (oldSubCategory == null) {
			return new ResponseEntity<Message>(new Message("This subCategory is not exist"), HttpStatus.BAD_REQUEST);
		}

		try {
			subCategoryService.update(oldSubCategory);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Create subCategory failed"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Message>(new Message("subCategory has editted"), HttpStatus.OK);
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
	public ResponseEntity<?> deleteSubCategory(@RequestBody SubCategory subCategory,
			@RequestHeader(value = "Authorization") String token) {

		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		try {
			subCategoryService.removeById(subCategory.getId());
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Remove subCategory failed"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Message>(new Message("SubCategory has removed"), HttpStatus.OK);
	}
}
