package th.in.nagi.fecs.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.codehaus.jackson.JsonParser;
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
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.service.UserService;

/**
 * Controller for users.
 * 
 * @author Nara Surawit
 *
 */
@Controller
@RequestMapping("/api2/users")
public class UsersController extends BaseController {

    /**
     * User service.
     */
    @Autowired
    private UserService userService;

    /**
     * Gets user service.
     * 
     * @return user service
     */
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
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Message getAllUsers() {

        List<User> users = getUserService().findAll();
        if(users != null) {
			return new SuccessMessage(Message.SUCCESS, users);
		}
		return new FailureMessage(Message.FAIL, "Not found user.");
    }

    /**
     * Creates new user.
     * 
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/new" }, method = RequestMethod.POST)
    public Message create(@RequestBody User user) {
    	System.out.println(user.toString());
    	System.out.println(user.getPassword());
    	String passwordHash = user.changeToHash(user.getPassword());
    	user.setPassword(passwordHash);
    	try {
			getUserService().store(user);
		} catch (Exception e) {
			// TODO: handle exception
			return new FailureMessage(Message.FAIL, "Create user failed");
		}
		return new SuccessMessage(Message.SUCCESS, user);
    }
    

    /*
     * This method will provide the medium to update an existing user.
     */
    @ResponseBody
    @RequestMapping(value = {"/edit" }, method = RequestMethod.POST)
    public Message editUser(@RequestBody User newUser) {
//        User user = getUserService().findByUsername(newUser.getUsername());
    	try {
    		getUserService().update(newUser);
		} catch (Exception e) {
			// TODO: handle exception
			return new FailureMessage(Message.FAIL, "User not found");
		}
		return new SuccessMessage(Message.SUCCESS, getUserService().findByUsername(newUser.getUsername()));
    }

    /*
     * This method will delete an user by it's Username value.
     */
    @ResponseBody
    @RequestMapping(value = {"/delete" }, method = RequestMethod.POST)
    public Message deleteUser(@RequestBody String username) {
        getUserService().removeByUsername(username);
        try {
        	getUserService().removeByUsername(username);
		} catch (Exception e) {
			// TODO: handle exception
			return new FailureMessage(Message.FAIL, "User not found");
		}
		return new SuccessMessage(Message.SUCCESS, username +" has removed");
    }
 }
