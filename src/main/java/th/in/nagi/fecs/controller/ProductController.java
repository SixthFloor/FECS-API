package th.in.nagi.fecs.controller;

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


@RestController
@RequestMapping("/api/products")
public class ProductController extends BaseController {
	
	@Autowired
    private ProductService productService;
	
	@ResponseBody
	@RequestMapping(value="/{serialNumber}", method=RequestMethod.POST)
    public Message getDetail(@PathVariable String serialNumber) {
		Product product = productService.findBySerialNumber(serialNumber);
		if (product != null){
			return new SuccessMessage(Message.SUCCESS, product);
		}
		return new ErrorMessage(Message.FAIL, "Not found product.");
        
    }
}
