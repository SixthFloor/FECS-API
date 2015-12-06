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

//import th.in.nagi.fecs.message.Message;
//import th.in.nagi.fecs.model.Cart;
//import th.in.nagi.fecs.model.Catalog;
//import th.in.nagi.fecs.model.Category;
//import th.in.nagi.fecs.model.ProductDescription;
//import th.in.nagi.fecs.model.SubCategory;
//import th.in.nagi.fecs.model.Type;
//import th.in.nagi.fecs.model.User;
//import th.in.nagi.fecs.service.AuthenticationService;
//import th.in.nagi.fecs.service.CartService;
//import th.in.nagi.fecs.service.CatalogService;
//import th.in.nagi.fecs.service.CategoryService;
//import th.in.nagi.fecs.service.ProductDescriptionService;
//import th.in.nagi.fecs.service.SubCategoryService;
//import th.in.nagi.fecs.service.TypeService;
//import th.in.nagi.fecs.service.UserService;
//import th.in.nagi.fecs.view.CartView;
//import th.in.nagi.fecs.view.CatalogView;
//import th.in.nagi.fecs.view.CategoryView;
//import th.in.nagi.fecs.view.ProductDescriptionView;
//import th.in.nagi.fecs.view.TypeView;

/**
 * Controller for category
 * 
 * @author Nara Surawit
 *
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {//extends BaseController {
//	
//	/**
//	 * service of catalog
//	 */
//	@Autowired
//	private CatalogService catalogService;
//	
//	/**
//	 * service of authenticate
//	 */
//	@Autowired
//	private AuthenticationService authenticationService;
//	
//	/**
//	 * service of type
//	 */
//	@Autowired
//	private TypeService typeService;
//	
//	/**
//	 * service of category
//	 */
//	@Autowired
//	private CategoryService categoryService;
//	
//	/**
//	 * service of subCategory
//	 */
//	@Autowired
//	private SubCategoryService subCategoryService;
//	
//	/**
//	 * service of product
//	 */
//	@Autowired
//	private ProductDescriptionService productDescriptionService;
//	
//	/**
//	 * service of cart
//	 */
//	@Autowired
//	private CartService cartService;
//	
//	/**
//	 * service of user
//	 */
//	@Autowired
//	private UserService userService;
//	
////	/**
////	 * Return list of catalog
////	 * 
////	 * @return list of catalog
////	 */
////	@JsonView(CatalogView.Summary.class)
////	@RequestMapping(value = "/all", method = RequestMethod.GET)
////	public ResponseEntity showAllCatalog() {
////		return new ResponseEntity(catalogService.findAll(), HttpStatus.OK);
////	}
//	
//	/**
//	 * create and add new Cart to database
//	 * 
//	 * @param cart
//	 * @param token
//	 * @return message success if not return message fail
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/new", method = RequestMethod.POST)
//	public ResponseEntity createCart(@RequestBody Cart cart,
//			@RequestHeader(value = "Authorization") String token) {
//		if (!authenticationService.checkPermission(token, authenticationService.MEMBER)) {
//			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
//		}
//		if(authenticationService.findByToken(token).getUser().getId() != cart.getUser().getId()){
//			return new ResponseEntity(new Message("This user cannot edit other person"), HttpStatus.FORBIDDEN);
//		}
//		
//		ProductDescription product = productDescriptionService.findByKey(cart.getProductDescription().getId());
//		User user = userService.findByKey(cart.getUser().getId());
//		Cart oldCart = cartService.findByProductAndUser(product, user);
//		
//		if(oldCart != null){
//			oldCart.setQuantity(oldCart.getQuantity()+cart.getQuantity());
//			try {
//				cartService.update(oldCart);
//			} catch (Exception e) {
//				return new ResponseEntity(new Message("Edited fail"), HttpStatus.BAD_REQUEST);
//			}
//			return new ResponseEntity(new Message("Quantity has added"), HttpStatus.CREATED);
//		}
//		else{
//			try{
//				cartService.createCart(user, product, cart.getQuantity());
//			}
//			catch(Exception e){
//				return new ResponseEntity(new Message("Created fail"), HttpStatus.BAD_REQUEST);
//			}
//		}
//		return new ResponseEntity(new Message("Cart has added"), HttpStatus.CREATED);
//	}
//	
//	/**
//	 * Edit Catalog to database
//	 * 
//	 * @param Catalog
//	 *            new catalog
//	 * @return message success if not return message fail
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
//	public ResponseEntity editCart(@RequestBody Cart newCart,
//			@RequestHeader(value = "Authorization") String token) {
//		if (!authenticationService.checkPermission(token, authenticationService.MEMBER)) {
//			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
//		}
//		if(authenticationService.findByToken(token).getUser().getId() != newCart.getUser().getId()){
//			return new ResponseEntity(new Message("This user cannot edit other person"), HttpStatus.FORBIDDEN);
//		}
//		
//		Cart cart = cartService.findByKey(newCart.getId());
//		cart.setQuantity(newCart.getQuantity());
//		
//		try {
//			cartService.update(cart);
//		} catch (Exception e) {
//			return new ResponseEntity(new Message("Edited fail"), HttpStatus.BAD_REQUEST);
//		}
//		
//		return new ResponseEntity(new Message("Cart has edited"), HttpStatus.CREATED);
//	}
//	
//	/**
//	 * Edit Catalog to database
//	 * 
//	 * @param Catalog
//	 *            new catalog
//	 * @return message success if not return message fail
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
//	public ResponseEntity deleteCart(@RequestBody Cart cart,
//			@RequestHeader(value = "Authorization") String token) {
//		if (!authenticationService.checkPermission(token, authenticationService.MEMBER)) {
//			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
//		}
//		if(authenticationService.findByToken(token).getUser().getId() != cart.getUser().getId()){
//			return new ResponseEntity(new Message("This user cannot edit other person"), HttpStatus.FORBIDDEN);
//		}
//		
//		try {
//			cartService.removeById(cart.getId());
//		} catch (Exception e) {
//			return new ResponseEntity(new Message("Deleted fail"), HttpStatus.BAD_REQUEST);
//		}
//		
//		return new ResponseEntity(new Message("Cart has deleted"), HttpStatus.CREATED);
//	}
//	
//	/**
//	 * Return type
//	 * 
//	 * @param product
//	 * @return type
//	 */
//	@JsonView(CartView.Summary.class)
//	@RequestMapping(value = "/{email:.+}", method = RequestMethod.GET)
//	public ResponseEntity getDetail(@PathVariable String email, @RequestHeader(value = "Authorization") String token) {
//		if (!authenticationService.checkPermission(token, authenticationService.MEMBER)) {
//			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
//		}
//		if(!authenticationService.findByToken(token).getUser().getEmail().equals(email)){
//			return new ResponseEntity(new Message("This user cannot edit other person"), HttpStatus.FORBIDDEN);
//		}
//		System.out.println(email);
//		User user = userService.findByEmail(email);
//		if(user == null){
//			return new ResponseEntity(new Message("This user not found"), HttpStatus.BAD_REQUEST);
//		}
//		List<Cart> carts = cartService.findByUser(user);
//		System.out.println(carts.size());
//		if (!carts.isEmpty()) {
//			return new ResponseEntity(carts, HttpStatus.OK);
//		}
//		return new ResponseEntity(new Message("Not found cart"), HttpStatus.BAD_REQUEST);
//
//	}
}