package th.in.nagi.fecs.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/api/user")
public class UsersController extends BaseController {

    /**
     * User service.
     */
    @Autowired
    private UserService userService;

    /**
     * Gets user service.
     * @return user service
     */
    protected UserService getUserService() {
        return userService;
    }

    /**
     * Lists all existing users. 
     * @return list of users
     */
    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Message getAllUsers() {
        Set<User> users = new HashSet<User>(getUserService().findAll());
        if(users != null) {
			return new SuccessMessage(Message.SUCCESS, users);
		}
		return new FailureMessage(Message.FAIL, "Not found user.");
    }
    
//    @ResponseBody
//    @RequestMapping(value = "/all", method = RequestMethod.GET)
//    public Message getListUsers(@RequestParam(value = "start", required = false)int start,
//    		@RequestParam(value = "size", required = false)int size) {
//        Set<User> users = new HashSet<User>(getUserService());
//        if(users != null) {
//			return new SuccessMessage(Message.SUCCESS, users);
//		}
//		return new FailureMessage(Message.FAIL, "Not found user.");
//    }
    
    /**
     * get user by email
     * @param email email of user that want to show
     * @return user if not return message fail
     */
    @ResponseBody
    @RequestMapping(value = "/{email}", method = RequestMethod.GET)
    public Message getUserByEmail(@PathVariable String email) {

        User user = getUserService().findByEmail(email);
        if(user != null) {
			return new SuccessMessage(Message.SUCCESS, user);
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
    	Date date = new Date();
    	String passwordHash = user.changeToHash(user.getPassword());
    	user.setPassword(passwordHash);
    	user.setJoiningDate(date);
    	try {
			getUserService().store(user);
		} catch (Exception e) {
			return new FailureMessage(Message.FAIL, "Create user failed");
		}
		return new SuccessMessage(Message.SUCCESS, user);
    }
    

    /**
     * edit user
     * @param newUser put user information that want to change, it is not required all parameter of user. 
     * @return message message and email of user or not return message fail and string "not found"
     */
    @ResponseBody
    @RequestMapping(value = {"/edit" }, method = RequestMethod.POST)
    public Message editUser(@RequestBody User newUser) {
//        User user = getUserService().findByUsername(newUser.getUsername());
    	try {
    		getUserService().update(newUser);
		} catch (Exception e) {
			return new FailureMessage(Message.FAIL, "User not found");
		}
		return new SuccessMessage(Message.SUCCESS, getUserService().findByEmail(newUser.getEmail()).getEmail());
    }

    /*
     * This method will delete an user by it's Username value.
     */
    /**
     * delete a user by id
     * @param tempUser username and password
     * @return message message success if not return message fail
     */
    @ResponseBody
    @RequestMapping(value = {"/delete" }, method = RequestMethod.POST)
    public Message deleteUser(@RequestBody User tempUser) {
    	User user = getUserService().findByEmail(tempUser.getEmail());
    	String passwordHash = user.changeToHash(tempUser.getPassword());
    	
    	if(!user.getPassword().equals(passwordHash)){
    		return new FailureMessage(Message.FAIL, "Incorrect password");
    	}
       
        try {
        	getUserService().removeByEmail(user.getEmail());
		} catch (Exception e) {
			return new FailureMessage(Message.FAIL, "User not found");
		}
		return new SuccessMessage(Message.SUCCESS, user.getEmail() +" has removed");
    }
 }
