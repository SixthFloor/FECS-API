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

import th.in.nagi.fecs.message.ErrorMessage;
import th.in.nagi.fecs.message.Message;
import th.in.nagi.fecs.message.SuccessMessage;
import th.in.nagi.fecs.model.Category;
//import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.model.SubCategory;
import th.in.nagi.fecs.service.AuthenticateService;
//import th.in.nagi.fecs.service.ProductService;
import th.in.nagi.fecs.service.SubCategoryService;


/**
 * Controller for product
 * @author Thanachote Visetsuthimont
 *
 */
@RestController
@RequestMapping("/api/product")
public class ProductController extends BaseController {
	
	/**
	 * Service of product
	 */
//	@Autowired
//    private ProductService productService;
	
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
//	@ResponseBody
//	@RequestMapping(value="/{serialNumber}", method=RequestMethod.GET)
//    public Message getDetail(@PathVariable String serialNumber) {
//		Product product = productService.findBySerialNumber(serialNumber);
//		if (product != null){
//			return new SuccessMessage(Message.SUCCESS, product, "200");
//		}
//		return new ErrorMessage(Message.ERROR, "Not found product.", "400");
//        
//    }
//	
//	/**
//	 * Return all products
//	 * @return list of all products
//	 */
//	@ResponseBody
//	@RequestMapping(value="/all", method=RequestMethod.GET)
//    public Message showAllProduct() {
//		List<Product> product = productService.findAll();
//		if (product != null){
//			return new SuccessMessage(Message.SUCCESS, product, "200");
//		}
//		return new ErrorMessage(Message.ERROR, "Not found product.", "400");
//    }
//	
//   /**
//    * list of products with limit size
//    * @param start position of the list 
//    * @param size size of the list
//    * @return limit list of product 
//    */
//	@ResponseBody
//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public Message getListProduct(@RequestParam(value = "start", required = false)int start,
//   		@RequestParam(value = "size", required = false)int size) {
//		int productListSize = productService.findAll().size();
//		if(size > productListSize - start){
//		   size = productListSize - start;
//		}
//		List<Product> products = (productService.findAndAscByName(start, size));
//		if(products == null) {
//    	   return new ErrorMessage(Message.ERROR, "Not found product.", "400");
//		}
//		return new SuccessMessage(Message.SUCCESS, products, "200");
//	}
//   
//   	/**
//   	 * create and add new product to database
//   	 * @param product new product
//   	 * @param id id of subcategory of product
//   	 * @return message success if not return message fail
//   	 */
//   	@ResponseBody
//	@RequestMapping(value="/new", method=RequestMethod.POST)
//   	public Message createNewProduct(@RequestBody Product product,
//		   @RequestParam(value = "subCategoryId", required = false)int id, @RequestHeader(value = "token") String token) {
//		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
//				authenticateService.OWNER)) {
//			return new ErrorMessage(Message.ERROR, "This user does not allow", "401");
//		}
//   		
//   		product.setSubCategory(subCategoryService.findByKey(id));
//   		
//   		System.out.println(product.getSubCategory().getName());
//   		try {
//			productService.store(product);
//		} catch (Exception e) {
//			return new ErrorMessage(Message.ERROR, "Create Product failed", "400");
//		}
//		return new SuccessMessage(Message.SUCCESS, "Product has added", "201");
//   	}
//   	
//	/**
//	 * edit information of product
//	 * @param product new information that want to edit
//	 * @param id id of new subcategory of product
//	 * @return message success if not return message fail
//	 */
//	@ResponseBody
//	@RequestMapping(value="/edit", method=RequestMethod.PUT)
//	public Message editProduct(@RequestBody Product product,
//		   @RequestParam(value = "subCategoryId", required = false)int id, @RequestHeader(value = "token") String token) {
//		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
//				authenticateService.OWNER)) {
//			return new ErrorMessage(Message.ERROR, "This user does not allow", "401");
//		}
//		
//		SubCategory subCategory = subCategoryService.findByKey(id);
//		if(subCategory != null){
//			product.setSubCategory(subCategory);
//		}
////		if(oldProduct == null){
////			return new FailureMessage(Message.FAIL, "This product is not existed");
////		}
////		
////		oldProduct.setName(product.getName());
//		product.setSubCategory(subCategoryService.findByKey(id));
//		try {
//			productService.update(product);
//		} catch (Exception e) {
//			return new ErrorMessage(Message.ERROR, "Edit product failed", "400");
//		}
//		
//		return new SuccessMessage(Message.SUCCESS, "Product" +" has edited", "200");
//	}
//	
//	/**
//	 * delete a product
//	 * @param product put id of product that want to delete 
//	 * @return message success if not return message fail
//	 */
//	@ResponseBody
//	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
//	public Message deleteProduct(@RequestBody Product product, @RequestHeader(value = "token") String token) {
//		if (!authenticateService.checkPermission(token, authenticateService.STAFF, authenticateService.MANAGER,
//				authenticateService.OWNER)) {
//			return new ErrorMessage(Message.ERROR, "This user does not allow", "401");
//		}
//		
//		System.out.println(product.getSerialNumber());
//		try {
//			productService.removeBySerialNumber(product.getSerialNumber());;
//		} catch (Exception e) {
//			System.out.println(e);
//			return new ErrorMessage(Message.ERROR, "Remove product failed", "400");
//		}
//		return new SuccessMessage(Message.SUCCESS, "Product" + " has removed", "200");
//	}
   
}
