package th.in.nagi.fecs.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.message.Message;
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.service.AuthenticationService;
import th.in.nagi.fecs.service.RoleService;
import th.in.nagi.fecs.service.UserService;
import th.in.nagi.fecs.view.UserView;

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
     * authenticate service.
     */
    @Autowired
    private AuthenticationService authenticationService;
    
    /**
     * role service
     */
    @Autowired
    private RoleService roleService;

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
    @JsonView(UserView.Personal.class)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity getAllUsers(@RequestHeader(value = "Authorization") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
        Set<User> users = new HashSet<User>(getUserService().findAll());
        if(users != null) {
        	return new ResponseEntity(users, HttpStatus.OK);
		}
        return new ResponseEntity(new Message("Not found user"), HttpStatus.BAD_REQUEST);
    }
    
    /**
	 * list of user with limit size
	 * 
	 * @param start
	 *            position of the list
	 * @param size
	 *            size of the list
	 * @return limit list of user
	 */
    @JsonView(UserView.Personal.class)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity getListUsers(@RequestParam(value = "start", required = false) int start,
			@RequestParam(value = "size", required = false) int size,@RequestHeader(value = "Authorization") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
		int userListSize = userService.findAll().size();
		if (size > userListSize - start) {
			size = userListSize - start;
		}
//		System.out.println(userListSize);
//		System.out.println(start+"                                                   "+size);
		List<User> user = (userService.findAndAscByFirstName(start, size));
		if (user == null) {
			return new ResponseEntity(new Message("Not found user"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(user, HttpStatus.OK);
	}
	
    
    /**
     * get user by email
     * @param email email of user that want to show
     * @return user if not return message fail
     */
    @JsonView(UserView.Personal.class)
    @RequestMapping(value = "/{email:.+}", method = RequestMethod.GET)
    public ResponseEntity getUserByEmail(@PathVariable String email, @RequestHeader(value = "Authorization") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

        User user = getUserService().findByEmail(email);
        if(user != null) {
        	return new ResponseEntity(user, HttpStatus.OK);
		}
        return new ResponseEntity(new Message("Not found user"), HttpStatus.BAD_REQUEST);
    }

    /**
     * Creates new user.
     * 
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/new" }, method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user) {//, @RequestParam(value = "roleId", required = false)int id) {
    	
    	Date date = new Date();
    	String passwordHash = user.changeToHash(user.getPassword());
    	user.setPassword(passwordHash);
    	user.setJoiningDate(date);
    	user.setRole(roleService.findByKey(1));
//    	System.out.println(user);
    	try {
			getUserService().store(user);
		} catch (Exception e) {
//			System.out.println(e);
			return new ResponseEntity(new Message("Create user failed"), HttpStatus.BAD_REQUEST);
		}
    	return new ResponseEntity(new Message("The user has created"), HttpStatus.CREATED);
    }
    
    /**
     * Creates new user.
     * 
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/newByOwner" }, method = RequestMethod.POST)
    public ResponseEntity createUserByOwner(@RequestBody User user,  @RequestHeader(value = "Authorization") String token
    		, @RequestParam(value = "roleId", required = false)int id) {
    	if (!authenticationService.checkPermission(token, authenticationService.OWNER)) {
			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
    	Date date = new Date();
    	String passwordHash = user.changeToHash(user.getPassword());
    	user.setPassword(passwordHash);
    	user.setJoiningDate(date);
    	user.setRole(roleService.findByKey(id));
//    	System.out.println(user);
    	try {
			getUserService().store(user);
		} catch (Exception e) {
//			System.out.println(e);
			return new ResponseEntity(new Message("Create user failed"), HttpStatus.BAD_REQUEST);
		}
    	return new ResponseEntity(new Message("The user has created"), HttpStatus.CREATED);
    }

    /**
     * edit user by member
     * @param newUser put user information that want to change, it is not required all parameter of user. 
     * @return message message and email of user or not return message fail and string "not found"
     */
    @ResponseBody
    @RequestMapping(value = {"/edit" }, method = RequestMethod.PUT)
    public ResponseEntity editUserByMember(@RequestBody User newUser, @RequestHeader(value = "Authorization") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.MEMBER
				, authenticationService.STAFF, authenticationService.MANAGER, authenticationService.OWNER)) {
			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
		if(authenticationService.findByToken(token).getUser().getId() != newUser.getId()){
			return new ResponseEntity(new Message("This user cannot edit other person"), HttpStatus.FORBIDDEN);
		}
//        User user = getUserService().findByUsername(newUser.getUsername());
		newUser.setRole(null);
    	try {
    		getUserService().update(newUser);
		} catch (Exception e) {
			return new ResponseEntity(new Message("User not found"), HttpStatus.BAD_REQUEST);
		}
    	return new ResponseEntity(getUserService().findByEmail(newUser.getEmail()).getEmail(), HttpStatus.OK);
    }
    
    /**
     * edit user by admin
     * @param newUser put user information that want to change, it is not required all parameter of user. 
     * @return message message and email of user or not return message fail and string "not found"
     */
    @ResponseBody
    @RequestMapping(value = {"/editByOwner" }, method = RequestMethod.PUT)
    public ResponseEntity editUserByAdmin(@RequestBody User newUser, @RequestHeader(value = "Authorization") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.OWNER)) {
			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
//		if(!authenticationService.getRole(token).getName().equals(authenticationService.OWNER)){
//			newUser.setRole(null);
//		}
//        User user = getUserService().findByUsername(newUser.getUsername());
    	try {
    		getUserService().update(newUser);
		} catch (Exception e) {
			return new ResponseEntity(new Message("User not found"), HttpStatus.BAD_REQUEST);
		}
    	return new ResponseEntity(new Message("getUserService().findByEmail(newUser.getEmail()).getEmail()"), HttpStatus.OK);
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
    @RequestMapping(value = {"/delete" }, method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@RequestBody User tempUser, @RequestHeader(value = "Authorization") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.OWNER)) {
			return new ResponseEntity(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
    	User user = getUserService().findByEmail(tempUser.getEmail());
    	String passwordHash = user.changeToHash(tempUser.getPassword());
    	
    	if(!user.getPassword().equals(passwordHash)){
    		return new ResponseEntity(new Message("Incorrect password"), HttpStatus.BAD_REQUEST);
    	}
       
        try {
        	getUserService().removeByEmail(user.getEmail());
		} catch (Exception e) {
			return new ResponseEntity(new Message("User not found"), HttpStatus.BAD_REQUEST);
		}
        return new ResponseEntity(new Message("User has removed"), HttpStatus.OK);
    }
 }
