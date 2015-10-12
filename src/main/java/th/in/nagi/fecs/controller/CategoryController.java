package th.in.nagi.fecs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import th.in.nagi.fecs.message.ErrorMessage;
import th.in.nagi.fecs.message.FailureMessage;
import th.in.nagi.fecs.message.Message;
import th.in.nagi.fecs.message.SuccessMessage;
import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.service.ProductService;


/**
 * Controller for category
 * @author Nara Surawit
 *
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController extends BaseController {
	
	/**
	 * Service of product
	 */
	@Autowired
    private ProductService productService;
	
	/**
	 * Return list of products in the category
	 * @param categoryName
	 * @return list of products in the category
	 */
	@ResponseBody
	@RequestMapping(value="/{categoryName}", method=RequestMethod.GET)
    public Message showProductsByCategory(@PathVariable String categoryName) {
		List<Product> product = productService.findAll();
		List<Product> tempProduct = new ArrayList();
 
		for(int i = 0; i < product.size(); i++) {
			if(product.get(i).getCategory().getName().equals(categoryName)){
				tempProduct.add(product.get(i));
			}
		}
		if (tempProduct != null){
			return new SuccessMessage(Message.SUCCESS, tempProduct);
		}
		return new FailureMessage(Message.FAIL, "Not found product.");
    }
}
