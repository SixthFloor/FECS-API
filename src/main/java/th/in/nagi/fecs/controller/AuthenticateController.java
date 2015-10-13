package th.in.nagi.fecs.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import th.in.nagi.fecs.message.FailureMessage;
import th.in.nagi.fecs.message.Message;
import th.in.nagi.fecs.message.SuccessMessage;
import th.in.nagi.fecs.model.Authenticate;
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.service.AuthenticateService;
import th.in.nagi.fecs.service.UserService;

/**
 * Controller for users.
 * 
 * @author Nara Surawit
 *
 */
@Controller
@RequestMapping("/api/authentication")
public class AuthenticateController extends BaseController {

    /**
     * User service.
     */
    @Autowired
    private AuthenticateService authenticateService;
    
    @Autowired
    private UserService userService;

    /**
     * Gets user service.
     * 
     * @return user service
     */
    protected AuthenticateService getAuthenticateService() {
        return authenticateService;
    }
    
    protected UserService getUserService() {
        return userService;
    }

    /**
     * Lists all existing users.
     * 
     * @param model
     * @return list of users
     */
    @ResponseBody
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public Message getAuthenticateByUsername(@PathVariable String username) {
    	
        List<Authenticate> authenticate = getAuthenticateService().findByUsername(username);
        if (authenticate != null){
			return new SuccessMessage(Message.SUCCESS, authenticate);
		}
		return new FailureMessage(Message.FAIL, "Not found authenticate.");
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Message login(@RequestBody User tempUser) {
    	System.out.println(tempUser.getUsername());
    	System.out.println(tempUser.getPassword());
    	User user = getUserService().findByUsername(tempUser.getUsername());
    	String passwordHash = user.changeToHash(tempUser.getPassword());
    	
    	if(!user.getPassword().equals(passwordHash)){
    		return new FailureMessage(Message.FAIL, "Incorrect password");
    	}
    	
    	Date date = new Date();
    	String text = tempUser.getUsername()+date.toString();
    	String textHash = "";
    	try {
    		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
    		MessageDigest md5 = MessageDigest.getInstance("MD5");
    		try {
				byte[] hash1 = sha256.digest(date.toString().getBytes("UTF-8"));
				byte[] hash2 = md5.digest(text.getBytes("UTF-8"));
				byte[] hash3 = sha256.digest((String.format("%064x", new java.math.BigInteger(1, hash2))).getBytes("UTF-8"));
				textHash =  "=="+String.format("%64x", new java.math.BigInteger(1, hash3))
							+"."
							+String.format("%064x", new java.math.BigInteger(1, hash1));
//				System.out.println(String.format("%064x", new java.math.BigInteger(1, hash3)));
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	date.setDate((date.getDate()+1));
        Authenticate authenticate = new Authenticate(textHash, user,date);    
        
        if (authenticate != null){
        	System.out.println(authenticate.getExpDate());
        	getAuthenticateService().store(authenticate);
			return new SuccessMessage(Message.SUCCESS, authenticate);
		}
		return new FailureMessage(Message.FAIL, "Not found authenticate.");
    }
    
    @ResponseBody
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public Message checkToken(@RequestBody Authenticate authenticate) {
    	Date date = new Date();
    	System.out.println(authenticate.getToken());
    	System.out.println(authenticateService.findByToken(authenticate.getToken()).getUser().getUsername());
    	if (date.before(authenticateService.findByToken(authenticate.getToken()).getExpDate())){
    		System.out.println("111111111111111111111");
			return new SuccessMessage(Message.SUCCESS, authenticateService.findByToken(authenticate.getToken()).getUser().getUsername());
		}
		return new FailureMessage(Message.FAIL, "token is expiration");
    	
    }
}
