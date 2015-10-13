package th.in.nagi.fecs.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/authenticate")
public class AuthenticateController extends BaseController {

    /**
     * User service.
     */
    @Autowired
    private AuthenticateService authenticateService;

    /**
     * Gets user service.
     * 
     * @return user service
     */
    protected AuthenticateService getAuthenticateService() {
        return authenticateService;
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
    	
        Authenticate authenticate = getAuthenticateService().findByUsername(username);
        if (authenticate != null){
			return new SuccessMessage(Message.SUCCESS, authenticate);
		}
		return new FailureMessage(Message.FAIL, "Not found authenticate.");
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/login/{username}", method = RequestMethod.GET)
    public Message login(@PathVariable String username) {
    	String text ="asdffsdfs";
    	try {
    		MessageDigest digest = MessageDigest.getInstance("SHA-256");
    		try {
				byte[] hash = digest.digest(text.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        Authenticate authenticate = getAuthenticateService().findByUsername(username);
        if (authenticate != null){
			return new SuccessMessage(Message.SUCCESS, authenticate);
		}
		return new FailureMessage(Message.FAIL, "Not found authenticate.");
    }

//    /**
//     * Creates new user.
//     * 
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = { "/new" }, method = RequestMethod.GET)
//    public String create(ModelMap model) {
//        User user = new User();
//        model.addAttribute("user", user);
//        model.addAttribute("edit", false);
//        return "registration";
//    }
//
//    /*
//     * This method will be called on form submission, handling POST request for
//     * saving user in database. It also validates the user input
//     */
//    @RequestMapping(value = { "/new" }, method = RequestMethod.POST)
//    public String saveUser(@Valid User user, BindingResult result,
//            ModelMap model) {
//
//        if (result.hasErrors()) {
//            return "registration";
//        }
//
//        /*
//         * Preferred way to achieve uniqueness of field [username] should be implementing custom @Unique annotation 
//         * and applying it on field [username] of Model class [User].
//         * 
//         * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
//         * framework as well while still using internationalized messages.
//         * 
//         */
//        if (!getUserService().isUsernameUnique(user.getId(),
//                user.getUsername())) {
//            FieldError usernameError = new FieldError("user", "username",
//                    getMessageSource().getMessage("non.unique.username",
//                            new String[] { user.getUsername() },
//                            Locale.getDefault()));
//            result.addError(usernameError);
//            return "registration";
//        }
//
//        getUserService().store(user);
//
//        model.addAttribute("success", "User " + user.getFirstName() + " "
//                + user.getLastName() + " registered successfully");
//        return "success";
//    }
//
//    /*
//     * This method will provide the medium to update an existing user.
//     */
//    @RequestMapping(value = {
//            "/edit-{username}-user" }, method = RequestMethod.GET)
//    public String editUser(@PathVariable String username, ModelMap model) {
//        User user = getUserService().findByUsername(username);
//        model.addAttribute("user", user);
//        model.addAttribute("edit", true);
//        return "registration";
//    }
//
//    /*
//     * This method will be called on form submission, handling POST request for
//     * updating user in database. It also validates the user input
//     */
//    @RequestMapping(value = {
//            "/edit-{username}-user" }, method = RequestMethod.POST)
//    public String updateUser(@Valid User user, BindingResult result,
//            ModelMap model, @PathVariable String username) {
//
//        if (result.hasErrors()) {
//            return "registration";
//        }
//
//        if (!getUserService().isUsernameUnique(user.getId(),
//                user.getUsername())) {
//            FieldError usernameError = new FieldError("user", "username",
//                    getMessageSource().getMessage("non.unique.username",
//                            new String[] { user.getUsername() },
//                            Locale.getDefault()));
//            result.addError(usernameError);
//            return "registration";
//        }
//
//        getUserService().update(user);
//
//        model.addAttribute("success", "User " + user.getFirstName() + " "
//                + user.getLastName() + " updated successfully");
//        return "success";
//    }
//
//    /*
//     * This method will delete an user by it's Username value.
//     */
//    @RequestMapping(value = {
//            "/delete-{username}-user" }, method = RequestMethod.GET)
//    public String deleteUser(@PathVariable String username) {
//        getUserService().removeByUsername(username);
//        return "redirect:/users/list";
//    }
}
