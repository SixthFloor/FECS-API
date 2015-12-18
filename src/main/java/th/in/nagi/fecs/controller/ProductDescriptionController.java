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
import th.in.nagi.fecs.model.ProductDescription;
import th.in.nagi.fecs.model.ProductImage;
import th.in.nagi.fecs.service.AuthenticationService;
import th.in.nagi.fecs.service.ProductDescriptionService;
import th.in.nagi.fecs.service.ProductImageService;
import th.in.nagi.fecs.view.ProductDescriptionView;
import th.in.nagi.fecs.view.ProductImageView;

/**
 * Controller for product
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@RestController
@RequestMapping("/api/product")
public class ProductDescriptionController extends BaseController {

	/**
	 * Service of product
	 */
	@Autowired
	private ProductDescriptionService productDescriptionService;

	/**
	 * Service of authenticate
	 */
	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private ProductImageService productImageService;
	
	/**
	 * Return a product that have the serialNumber
	 * 
	 * @param serialNumber
	 * @return a product that have the serialNumber
	 */
	@JsonView(ProductDescriptionView.Personal.class)
	@RequestMapping(value = "/{serialNumber}", method = RequestMethod.GET)
	public ResponseEntity<?> getDetail(@PathVariable String serialNumber) {
		ProductDescription productDescription = productDescriptionService.findBySerialNumber(serialNumber);

		if (productDescription != null) {
			return new ResponseEntity<ProductDescription>(productDescription, HttpStatus.OK);
		}

		return new ResponseEntity<Message>(new Message("Not found product"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Return all products
	 * 
	 * @return list of all products
	 */
	@JsonView(ProductDescriptionView.Personal.class)
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<?> showAllProduct() {
		List<ProductDescription> productDescriptions = productDescriptionService.findAll();

		if (productDescriptions != null) {
			return new ResponseEntity<List<ProductDescription>>(productDescriptions, HttpStatus.OK);
		}

		return new ResponseEntity<Message>(new Message("Not found product"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * list of products with limit size
	 * 
	 * @param start
	 *            position of the list
	 * @param size
	 *            size of the list
	 * @return limit list of product
	 */
	@JsonView(ProductDescriptionView.Personal.class)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<?> getListProduct(@RequestParam(value = "start", required = false) int start,
			@RequestParam(value = "size", required = false) int size) {
		int productListSize = productDescriptionService.findAll().size();

		if (size > productListSize - start) {
			size = productListSize - start;
		}

		List<ProductDescription> productDescriptions = (productDescriptionService.findAndAscByName(start, size));

		if (productDescriptions == null) {
			return new ResponseEntity<Message>(new Message("Not found product"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<List<ProductDescription>>(productDescriptions, HttpStatus.OK);
	}

	/**
	 * create and add new product to database
	 * 
	 * @param furnitureDescription
	 *            new product
	 * @param id
	 *            id of subcategory of product
	 * @return message success if not return message fail
	 */
	@JsonView(ProductDescriptionView.Personal.class)
	@ResponseBody
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ResponseEntity<?> createNewProduct(@RequestBody ProductDescription productDescription,
			@RequestHeader(value = "Authorization") String token) {

		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
		productDescription.setName(productDescription.getName().toUpperCase());
		ProductDescription product = productDescriptionService.findByName(productDescription.getName());
		if(product != null){
			return new ResponseEntity<Message>(new Message("This name has used"), HttpStatus.BAD_REQUEST);
		}
		
		productDescription.setSerialNumber("");
		productDescription.setStatus(ProductDescription.SELL);

		try {
			productDescriptionService.store(productDescription);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<Message>(new Message("Create FurnitureDescription failed"),
					HttpStatus.BAD_REQUEST);
		}
		
		productDescription.setSerialNumber(getSerial(productDescription.getName(), productDescription.getId()));
		
		try {
			productDescriptionService.update(productDescription);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<Message>(new Message("Generate serialNumber fail"), HttpStatus.BAD_REQUEST);
		}
		

		return new ResponseEntity<ProductDescription>(productDescription, HttpStatus.CREATED);
	}

	/**
	 * edit information of product
	 * 
	 * @param furnitureDescription
	 *            new information that want to edit
	 * @param id
	 *            id of new subcategory of product
	 * @return message success if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public ResponseEntity<?> editProduct(@RequestBody ProductDescription productDescription,
			@RequestHeader(value = "Authorization") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
		
		productDescription.setStatus(ProductDescription.SELL);
		try {
			ProductDescription oldProductDescription = productDescriptionService.findByKey(productDescription.getId());
			for(ProductImage image: productDescription.getImages()){
			if (image.getId() != null) {
				image.setProductDescription(oldProductDescription);
				productImageService.store(image);	
			}
			System.out.println(image.getId());
			}
			productDescriptionService.update(productDescription);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Edit product failed"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Message>(new Message("FurnitureDescription has editted"), HttpStatus.OK);
	}

	/**
	 * delete a product
	 * 
	 * @param furnitureDescription
	 *            put id of product that want to delete
	 * @return message success if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProduct(@RequestBody ProductDescription productDescription,
			@RequestHeader(value = "Authorization") String token) {

		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		System.out.println(productDescription.getSerialNumber());

		try {
			productDescriptionService.removeBySerialNumber(productDescription.getSerialNumber());
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<Message>(new Message("Remove product failed"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Message>(new Message("FurniturerDescription has removed"), HttpStatus.OK);
	}

	@JsonView(ProductDescriptionView.Personal.class)
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<?> search(@RequestParam(value = "query", required = false) String searchName) {
		List<ProductDescription> productDescriptions = (productDescriptionService.search(searchName));
		if (productDescriptions == null) {
			return new ResponseEntity<Message>(new Message("Not found product"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<ProductDescription>>(productDescriptions, HttpStatus.OK);
	}

	private String getSerial(String productName,Integer productId) {

		productName = productName.toUpperCase();

		String productCode = "" + productName.charAt(0) + productName.charAt(productName.length() - 1);
//		String id = "" + (int) (Math.ceil(Math.random() * 10) - 1) + "" + (int) (Math.ceil(Math.random() * 10) - 1) + ""
//				+ (int) (Math.ceil(Math.random() * 10) - 1) + (int) (Math.ceil(Math.random() * 10) - 1);

		String serial = String.format("%s%04d", productCode, productId);

		return serial;
	}
	
	/**
	 * Add a image to ProductDescription
	 * 
	 * @param productImage
	 *            put id of product that want to add image
	 * @param productId
	 * @param Authorization
	 * 
	 * @return message success if not return message fail
	 */
	@JsonView(ProductImageView.Personal.class)
	@RequestMapping(value = "/newImage", method = RequestMethod.POST)
	public ResponseEntity<?> createNewImage(@RequestBody ProductImage productImage,
			@RequestParam(value = "productId") Integer id,
			@RequestHeader(value = "Authorization") String token) {

		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
		
		productImage.setProductDescription(productDescriptionService.findByKey(id));
		
		try {
			productImageService.store(productImage);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Edit fail"), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<ProductImage>(productImage, HttpStatus.OK);
	}
	
	
}
