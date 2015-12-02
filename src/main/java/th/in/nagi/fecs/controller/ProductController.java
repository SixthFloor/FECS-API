package th.in.nagi.fecs.controller;

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
import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.model.ProductDescription;
import th.in.nagi.fecs.service.AuthenticationService;
import th.in.nagi.fecs.service.ProductDescriptionService;
import th.in.nagi.fecs.service.ProductService;
import th.in.nagi.fecs.view.ProductView;

/**
 * Controller for product
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
@RestController
@RequestMapping("/api/real-product")
public class ProductController extends BaseController {

	/**
	 * service of product
	 */
	@Autowired
	private ProductService productService;

	/**
	 * service of product description
	 */
	@Autowired
	private ProductDescriptionService productDescriptionService;
	
	/**
	 * service of authenticate
	 */
	@Autowired
	private AuthenticationService authenticationService;

	/**
	 * Return list of product
	 * 
	 * @return list of product
	 */
	@JsonView(ProductView.Personal.class)
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity showAllProduct() {
		return new ResponseEntity(productService.findAll(), HttpStatus.OK);
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
	public ResponseEntity createNewProduct(@RequestBody ProductDescription pd,
			@RequestHeader(value = "Authorization") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		ProductDescription productDescription = productDescriptionService.findByKey(pd.getId());

		Product newProduct = new Product();
		newProduct.setProductDescription(productDescription);

		try {
			productService.store(newProduct);
		} catch (Exception e) {
			return new ResponseEntity(new Message("Added fail"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(new Message("Product has added"), HttpStatus.CREATED);
	}
}
