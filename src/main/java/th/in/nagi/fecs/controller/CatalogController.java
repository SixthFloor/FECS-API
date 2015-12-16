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
import th.in.nagi.fecs.model.Catalog;
import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.ProductDescription;
import th.in.nagi.fecs.model.SubCategory;
import th.in.nagi.fecs.model.Type;
import th.in.nagi.fecs.service.AuthenticationService;
import th.in.nagi.fecs.service.CatalogService;
import th.in.nagi.fecs.service.CategoryService;
import th.in.nagi.fecs.service.ProductDescriptionService;
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
@RequestMapping("/api/catalog")
public class CatalogController extends BaseController {

	/**
	 * service of catalog
	 */
	@Autowired
	private CatalogService catalogService;

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
	 * service of product
	 */
	@Autowired
	private ProductDescriptionService productDescriptionService;

	/**
	 * Return list of catalog
	 * 
	 * @return list of catalog
	 */
	@JsonView(CatalogView.Summary.class)
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<?> showAllCatalog() {
		return new ResponseEntity<List<Catalog>>(catalogService.findAll(), HttpStatus.OK);
	}

	/**
	 * create and add new Catalog to database
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
	public ResponseEntity<?> createNewCatalog(@RequestBody ProductDescription productDescription,
			@RequestParam(value = "subCategory", required = false) String subCategoryName,
			@RequestParam(value = "category", required = false) String categoryName,
			@RequestHeader(value = "Authorization") String token) {

		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		ProductDescription product = productDescriptionService.findByKey(productDescription.getId());
		SubCategory subCategory = subCategoryService.findByName(subCategoryName);
		Category category = categoryService.findByName(categoryName);

		try {
			catalogService.createCatalog(category, subCategory, product);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Created fail"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Message>(new Message("Catalog has added"), HttpStatus.CREATED);
	}

	/**
	 * Edit Catalog to database
	 * 
	 * @param Catalog
	 *            new catalog
	 * @return message success if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public ResponseEntity<?> editCatalog(@RequestBody Catalog catalog,
			@RequestHeader(value = "Authorization") String token) {

		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		Type type = typeService.findByKey(catalog.getType().getId());
		ProductDescription product = productDescriptionService.findByKey(catalog.getProductDescription().getId());
		catalog.setType(type);
		catalog.setProductDescription(product);

		try {
			catalogService.update(catalog);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Edited fail"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Message>(new Message("Catalog has edited"), HttpStatus.CREATED);
	}

	/**
	 * Return type
	 * 
	 * @param product
	 * @return type
	 */
	@JsonView(CatalogView.Summary.class)
	@RequestMapping(value = "/{serialNumber}", method = RequestMethod.GET)
	public ResponseEntity<?> getDetail(@PathVariable String serialNumber) {

		ProductDescription productDescription = productDescriptionService.findBySerialNumber(serialNumber);
		List<Catalog> catalogs = catalogService.findCatalogByProductDescription(productDescription);

		return new ResponseEntity<List<Catalog>>(catalogs, HttpStatus.OK);
	}
}
