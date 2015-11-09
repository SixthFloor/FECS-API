package th.in.nagi.fecs.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import th.in.nagi.fecs.message.ErrorMessage;
import th.in.nagi.fecs.message.Message;
import th.in.nagi.fecs.message.SuccessMessage;
import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.FurnitureDescription;
import th.in.nagi.fecs.model.SubCategory;
import th.in.nagi.fecs.service.AuthenticateService;
import th.in.nagi.fecs.service.FurnitureDescriptionService;
import th.in.nagi.fecs.service.SubCategoryService;
import th.in.nagi.fecs.view.FurnitureDescriptionView;


/**
 * Controller for product
 * @author Thanachote Visetsuthimont
 *
 */
@RestController
@RequestMapping("/api/furniture")
public class FurnitureDescriptionController extends BaseController {
	
	/**
	 * Service of product
	 */
	@Autowired
    private FurnitureDescriptionService furnitureDescriptionService;
	
	/**
	 * Service of subcategory
	 */
	@Autowired
    private SubCategoryService subCategoryService;
	
	/**
	 * Service of authenticate
	 */
	@Autowired
    private AuthenticateService authenticateService;
	
	/**
	 * Return a product that have the serialNumber
	 * @param serialNumber
	 * @return a product that have the serialNumber
	 */
	@JsonView(FurnitureDescriptionView.Summary.class)
	@RequestMapping(value="/{serialNumber}", method=RequestMethod.GET)
    public ResponseEntity getDetail(@PathVariable String serialNumber) {
		FurnitureDescription furnitureDescription = furnitureDescriptionService.findBySerialNumber(serialNumber);
		if (furnitureDescription != null){
			return  new ResponseEntity(furnitureDescription, HttpStatus.OK);
		}
		return new ResponseEntity(new Message("Not found product"), HttpStatus.BAD_REQUEST);
        
    }
	
	/**
	 * Return all products
	 * @return list of all products
	 */
	@JsonView(FurnitureDescriptionView.Summary.class)
	@RequestMapping(value="/all", method=RequestMethod.GET)
    public ResponseEntity showAllProduct() {
		List<FurnitureDescription> furnitureDescription = furnitureDescriptionService.findAll();
		if (furnitureDescription != null){
			return new ResponseEntity(furnitureDescription, HttpStatus.OK);
		}
		return new ResponseEntity(new Message("Not found product"), HttpStatus.BAD_REQUEST);
    }
	
   /**
    * list of products with limit size
    * @param start position of the list 
    * @param size size of the list
    * @return limit list of product 
    */
	@JsonView(FurnitureDescriptionView.Summary.class)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity getListProduct(@RequestParam(value = "start", required = false)int start,
   		@RequestParam(value = "size", required = false)int size) {
		int productListSize = furnitureDescriptionService.findAll().size();
		if(size > productListSize - start){
		   size = productListSize - start;
		}
		List<FurnitureDescription> furnitureDescriptions = (furnitureDescriptionService.findAndAscByName(start, size));
		if(furnitureDescriptions == null) {
			return new ResponseEntity(new Message("Not found product"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(furnitureDescriptions, HttpStatus.OK);
	}
   
   	/**
   	 * create and add new product to database
   	 * @param furnitureDescription new product
   	 * @param id id of subcategory of product
   	 * @return message success if not return message fail
   	 */
   	@ResponseBody
	@RequestMapping(value="/new", method=RequestMethod.POST)
   	public ResponseEntity createNewProduct(@RequestBody FurnitureDescription furnitureDescription,
		   @RequestParam(value = "subCategoryId", required = false)int id, @RequestHeader(value = "token") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
   		
   		furnitureDescription.setSubCategory(subCategoryService.findByKey(id));
   		
   		System.out.println(furnitureDescription.getSubCategory().getName());
   		try {
			furnitureDescriptionService.store(furnitureDescription);
		} catch (Exception e) {
			return new ResponseEntity(new Message("Create FurnitureDescription failed"), HttpStatus.BAD_REQUEST);
		}
   		return new ResponseEntity(new Message("FurnitureDescription has added"), HttpStatus.CREATED);
   	}
   	
	/**
	 * edit information of product
	 * @param furnitureDescription new information that want to edit
	 * @param id id of new subcategory of product
	 * @return message success if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value="/edit", method=RequestMethod.PUT)
	public ResponseEntity editProduct(@RequestBody FurnitureDescription furnitureDescription,
		   @RequestParam(value = "subCategoryId", required = false)int id, @RequestHeader(value = "token") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
		
		SubCategory subCategory = subCategoryService.findByKey(id);
		if(subCategory != null){
			furnitureDescription.setSubCategory(subCategory);
		}
//		if(oldProduct == null){
//			return new FailureMessage(Message.FAIL, "This product is not existed");
//		}
//		
//		oldProduct.setName(product.getName());
		furnitureDescription.setSubCategory(subCategoryService.findByKey(id));
		try {
			furnitureDescriptionService.update(furnitureDescription);
		} catch (Exception e) {
			return new ResponseEntity(new Message("Edit product failed"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new Message("FurnitureDescription has editted"), HttpStatus.OK);
	}
	
	/**
	 * delete a product
	 * @param furnitureDescription put id of product that want to delete 
	 * @return message success if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	public ResponseEntity deleteProduct(@RequestBody FurnitureDescription furnitureDescription, @RequestHeader(value = "token") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
		
		System.out.println(furnitureDescription.getSerialNumber());
		try {
			furnitureDescriptionService.removeBySerialNumber(furnitureDescription.getSerialNumber());;
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity(new Message("Remove product failed"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new Message("FurniturerDescription has removed"), HttpStatus.OK);
	}
   
}
