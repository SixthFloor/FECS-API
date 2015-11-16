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
import th.in.nagi.fecs.model.SubCategory;
import th.in.nagi.fecs.service.AuthenticationService;
import th.in.nagi.fecs.service.ProductDescriptionService;
import th.in.nagi.fecs.service.SubCategoryService;
import th.in.nagi.fecs.view.ProductDescriptionView;

/**
 * Controller for product
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@RestController
@RequestMapping("/api/furniture")
public class ProductDescriptionController extends BaseController {

	/**
	 * Service of product
	 */
	@Autowired
	private ProductDescriptionService productDescriptionService;

	/**
	 * Service of subcategory
	 */
	@Autowired
	private SubCategoryService subCategoryService;

	/**
	 * Service of authenticate
	 */
	@Autowired
	private AuthenticationService authenticationService;

	/**
	 * Return a product that have the serialNumber
	 * 
	 * @param serialNumber
	 * @return a product that have the serialNumber
	 */
	@JsonView(ProductDescriptionView.Summary.class)
	@RequestMapping(value = "/{serialNumber}", method = RequestMethod.GET)
	public ResponseEntity getDetail(@PathVariable String serialNumber) {
		ProductDescription productDescription = productDescriptionService.findBySerialNumber(serialNumber);
		if (productDescription != null) {
			return new ResponseEntity(productDescription, HttpStatus.OK);
		}
		return new ResponseEntity(new Message("Not found product"), HttpStatus.BAD_REQUEST);

	}

	/**
	 * Return all products
	 * 
	 * @return list of all products
	 */
	@JsonView(ProductDescriptionView.Summary.class)
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity showAllProduct() {
		List<ProductDescription> productDescription = productDescriptionService.findAll();
		if (productDescription != null) {
			return new ResponseEntity(productDescription, HttpStatus.OK);
		}
		return new ResponseEntity(new Message("Not found product"), HttpStatus.BAD_REQUEST);
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
	@JsonView(ProductDescriptionView.Summary.class)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity getListProduct(@RequestParam(value = "start", required = false) int start,
			@RequestParam(value = "size", required = false) int size) {
		int productListSize = productDescriptionService.findAll().size();
		if (size > productListSize - start) {
			size = productListSize - start;
		}
		List<ProductDescription> productDescription = (productDescriptionService.findAndAscByName(start, size));
		if (productDescription == null) {
			return new ResponseEntity(new Message("Not found product"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(productDescription, HttpStatus.OK);
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
	@ResponseBody
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ResponseEntity createNewProduct(@RequestBody ProductDescription productDescription,
			@RequestParam(value = "subCategoryId", required = false) int id,
			@RequestHeader(value = "token") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

//		productDescription.setSubCategory(subCategoryService.findByKey(id));

		// System.out.println(furnitureDescription.getSubCategory().getName());
		try {
			productDescriptionService.store(productDescription);
		} catch (Exception e) {
			return new ResponseEntity(new Message("Create FurnitureDescription failed"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new Message("FurnitureDescription has added"), HttpStatus.CREATED);
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
	public ResponseEntity editProduct(@RequestBody ProductDescription productDescription,
			@RequestParam(value = "subCategoryId", required = false) int id,
			@RequestHeader(value = "token") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		SubCategory subCategory = subCategoryService.findByKey(id);
		if (subCategory != null) {
//			productDescription.setSubCategory(subCategory);
		}

		try {
			productDescriptionService.update(productDescription);
		} catch (Exception e) {
			return new ResponseEntity(new Message("Edit product failed"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new Message("FurnitureDescription has editted"), HttpStatus.OK);
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
	public ResponseEntity deleteProduct(@RequestBody ProductDescription productDescription,
			@RequestHeader(value = "token") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		System.out.println(productDescription.getSerialNumber());
		try {
			productDescriptionService.removeBySerialNumber(productDescription.getSerialNumber());
			;
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity(new Message("Remove product failed"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new Message("FurniturerDescription has removed"), HttpStatus.OK);
	}

}
