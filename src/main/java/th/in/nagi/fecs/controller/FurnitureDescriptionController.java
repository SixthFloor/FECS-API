package th.in.nagi.fecs.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/product")
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
    public Message getDetail(@PathVariable String serialNumber) {
		FurnitureDescription furnitureDescription = furnitureDescriptionService.findBySerialNumber(serialNumber);
		if (furnitureDescription != null){
			return new SuccessMessage(Message.SUCCESS, furnitureDescription, "200");
		}
		return new ErrorMessage(Message.ERROR, "Not found product.", "400");
        
    }
	
	/**
	 * Return all products
	 * @return list of all products
	 */
	@JsonView(FurnitureDescriptionView.Summary.class)
	@RequestMapping(value="/all", method=RequestMethod.GET)
    public Message showAllProduct() {
		List<FurnitureDescription> furnitureDescription = furnitureDescriptionService.findAll();
		if (furnitureDescription != null){
			return new SuccessMessage(Message.SUCCESS, furnitureDescription, "200");
		}
		return new ErrorMessage(Message.ERROR, "Not found product.", "400");
    }
	
   /**
    * list of products with limit size
    * @param start position of the list 
    * @param size size of the list
    * @return limit list of product 
    */
	@JsonView(FurnitureDescriptionView.Summary.class)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Message getListProduct(@RequestParam(value = "start", required = false)int start,
   		@RequestParam(value = "size", required = false)int size) {
		int productListSize = furnitureDescriptionService.findAll().size();
		if(size > productListSize - start){
		   size = productListSize - start;
		}
		List<FurnitureDescription> furnitureDescriptions = (furnitureDescriptionService.findAndAscByName(start, size));
		if(furnitureDescriptions == null) {
    	   return new ErrorMessage(Message.ERROR, "Not found product.", "400");
		}
		return new SuccessMessage(Message.SUCCESS, furnitureDescriptions, "200");
	}
   
   	/**
   	 * create and add new product to database
   	 * @param furnitureDescription new product
   	 * @param id id of subcategory of product
   	 * @return message success if not return message fail
   	 */
   	@ResponseBody
	@RequestMapping(value="/new", method=RequestMethod.POST)
   	public Message createNewProduct(@RequestBody FurnitureDescription furnitureDescription,
		   @RequestParam(value = "subCategoryId", required = false)int id, @RequestHeader(value = "token") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new ErrorMessage(Message.ERROR, "This user does not allow", "401");
		}
   		
   		furnitureDescription.setSubCategory(subCategoryService.findByKey(id));
   		
   		System.out.println(furnitureDescription.getSubCategory().getName());
   		try {
			furnitureDescriptionService.store(furnitureDescription);
		} catch (Exception e) {
			return new ErrorMessage(Message.ERROR, "Create FurnitureDescription failed", "400");
		}
		return new SuccessMessage(Message.SUCCESS, "FurnitureDescription has added", "200");
   	}
   	
	/**
	 * edit information of product
	 * @param furnitureDescription new information that want to edit
	 * @param id id of new subcategory of product
	 * @return message success if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public Message editProduct(@RequestBody FurnitureDescription furnitureDescription,
		   @RequestParam(value = "subCategoryId", required = false)int id, @RequestHeader(value = "token") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new ErrorMessage(Message.ERROR, "This user does not allow", "401");
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
			return new ErrorMessage(Message.ERROR, "Edit product failed", "400");
		}
		
		return new SuccessMessage(Message.SUCCESS, "FurnitureDescription" +" has edited", "200");
	}
	
	/**
	 * delete a product
	 * @param furnitureDescription put id of product that want to delete 
	 * @return message success if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public Message deleteProduct(@RequestBody FurnitureDescription furnitureDescription, @RequestHeader(value = "token") String token) {
		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
				authenticateService.OWNER)) {
			return new ErrorMessage(Message.ERROR, "This user does not allow", "401");
		}
		
		System.out.println(furnitureDescription.getSerialNumber());
		try {
			furnitureDescriptionService.removeBySerialNumber(furnitureDescription.getSerialNumber());;
		} catch (Exception e) {
			System.out.println(e);
			return new ErrorMessage(Message.ERROR, "Remove product failed", "400");
		}
		return new SuccessMessage(Message.SUCCESS, "FurnitureDescription" + " has removed", "200");
	}
   
}
