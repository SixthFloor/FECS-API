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
import th.in.nagi.fecs.message.Message;
import th.in.nagi.fecs.message.SuccessMessage;
import th.in.nagi.fecs.model.Product;
import th.in.nagi.fecs.service.ProductService;


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
	@Autowired
    private ProductService productService;
	
	/**
	 * Return a product that have the serialNumber
	 * @param serialNumber
	 * @return a product that have the serialNumber
	 */
	@ResponseBody
	@RequestMapping(value="/{serialNumber}", method=RequestMethod.GET)
    public Message getDetail(@PathVariable String serialNumber) {
		Product product = productService.findBySerialNumber(serialNumber);
		if (product != null){
			return new SuccessMessage(Message.SUCCESS, product);
		}
		return new ErrorMessage(Message.FAIL, "Not found product.");
        
    }
	
	/**
	 * Return all products
	 * @return list of all products
	 */
	@ResponseBody
	@RequestMapping(value="/all", method=RequestMethod.GET)
    public Message showAllProduct() {
		List<Product> product = productService.findAll();
		if (product != null){
			return new SuccessMessage(Message.SUCCESS, product);
		}
		return new ErrorMessage(Message.FAIL, "Not found product.");
    }
}
