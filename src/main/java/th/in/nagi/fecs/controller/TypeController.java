package th.in.nagi.fecs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.message.Message;
import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.SubCategory;
import th.in.nagi.fecs.model.Type;
import th.in.nagi.fecs.service.AuthenticationService;
import th.in.nagi.fecs.service.CategoryService;
import th.in.nagi.fecs.service.SubCategoryService;
import th.in.nagi.fecs.service.TypeService;
import th.in.nagi.fecs.view.CatalogView;

/**
 * Controller for category
 * 
 * @author Nara Surawit
 *
 */
@RestController
@RequestMapping("/api/type")
public class TypeController extends BaseController {

	/**
	 * service of authenticate
	 */
	@Autowired
	private AuthenticationService authenticationService;

	/**
	 * service of type
	 */
	@Autowired
	private TypeService typeService;

	/**
	 * service of category
	 */
	@Autowired
	private CategoryService categoryService;

	/**
	 * service of subCategory
	 */
	@Autowired
	private SubCategoryService subCategoryService;

	/**
	 * Return list of type
	 * 
	 * @return list of type
	 */
	@JsonView(CatalogView.Summary.class)
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<?> showAllCatalog() {
		return new ResponseEntity<List<Type>>(typeService.findAll(), HttpStatus.OK);
	}

	/**
	 * create and add new type to database
	 * 
	 * @param productDescription
	 *            new product
	 * @param subCategoryName
	 *            name of subcategory of product
	 * @param categoryName
	 *            name of category of product
	 * @return message success if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ResponseEntity<?> createNewType(@RequestBody Type type,
			@RequestHeader(value = "Authorization") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
		SubCategory subCategory = subCategoryService.findByKey(type.getSubCategory().getId());
		Category category = categoryService.findByKey(type.getCategory().getId());

		Type newType = new Type();
		newType.setSubCategory(subCategory);
		newType.setCategory(category);

		try {
			typeService.store(newType);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Created fail"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Message>(new Message("Type has created"), HttpStatus.CREATED);
	}

	/**
	 * Edit type to database
	 * 
	 * @param type
	 *            new type
	 * @return message success if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public ResponseEntity<?> editCatalog(@RequestBody Type type, @RequestHeader(value = "Authorization") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		Type newType = typeService.findByKey(type.getId());
		SubCategory subCategory = subCategoryService.findByKey(type.getSubCategory().getId());
		Category category = categoryService.findByKey(type.getCategory().getId());

		newType.setSubCategory(subCategory);
		newType.setCategory(category);
		try {
			typeService.update(newType);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Edited fail"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Message>(new Message("Type has edited"), HttpStatus.CREATED);
	}
}
