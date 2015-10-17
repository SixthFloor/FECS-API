package th.in.nagi.fecs.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.model.SubCategory;
import th.in.nagi.fecs.service.CategoryService;
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
	
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * Return list of products in the category
	 * @param categoryName
	 * @return list of products in the category
	 */
	@ResponseBody
	@RequestMapping(value="/{categoryName}", method=RequestMethod.GET)
    public Message showProductsByCategory(@PathVariable String categoryName) {
		
		Category category = categoryService.findByName(categoryName);
		if(category != null){
			return new SuccessMessage(Message.SUCCESS, category.getProducts());
		}
		return new FailureMessage(Message.FAIL, "Not found product.");
    }
}
