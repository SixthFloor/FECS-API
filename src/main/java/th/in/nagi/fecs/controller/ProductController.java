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
	public ResponseEntity<?> showAllProduct(
//			@RequestHeader(value = "Authorization") String token
			) {
//		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
//				authenticationService.OWNER)) {
//			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
//		}
		
		return new ResponseEntity<List<Product>>(productService.findAll(), HttpStatus.OK);
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
	public ResponseEntity<?> createNewProduct(@RequestHeader(value = "Authorization") String token,
			@RequestBody List<ProductDescription> productDescriptions) {
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		for (ProductDescription pd : productDescriptions) {
			ProductDescription productDescription = productDescriptionService.findByKey(pd.getId());
			Product product = new Product();
			product.setProductDescription(productDescription);

			try {
				productService.store(product);
			} catch (Exception e) {
				return new ResponseEntity<Message>(new Message("Failed add product [" + pd.getId() + "]"),
						HttpStatus.BAD_REQUEST);
			}
		}

		return new ResponseEntity<Message>(new Message("Products are added"), HttpStatus.CREATED);
	}
}
