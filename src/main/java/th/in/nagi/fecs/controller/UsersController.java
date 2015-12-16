package th.in.nagi.fecs.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	 * 
	 * @return user service
	 */
	protected UserService getUserService() {
		return userService;
	}

	/**
	 * Lists all existing users.
	 * 
	 * @return list of users
	 */
	@JsonView(UserView.Personal.class)
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUsers(@RequestHeader(value = "Authorization") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
		Set<User> users = new HashSet<User>(getUserService().findAll());
		{
			return new ResponseEntity<Set<User>>(users, HttpStatus.OK);
		}
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
	public ResponseEntity<?> getListUsers(@RequestParam(value = "start", required = false) int start,
			@RequestParam(value = "size", required = false) int size,
			@RequestHeader(value = "Authorization") String token) {

		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		int userListSize = userService.findAll().size();

		if (size > userListSize - start) {
			size = userListSize - start;
		}

		List<User> users = (userService.findAndAscByFirstName(start, size));

		if (users == null) {
			return new ResponseEntity<Message>(new Message("Not found user"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	/**
	 * get user by email
	 * 
	 * @param email
	 *            email of user that want to show
	 * @return user if not return message fail
	 */
	@JsonView(UserView.AllInformation.class)
	@RequestMapping(value = "/admin/{email:.+}", method = RequestMethod.GET)
	public ResponseEntity<?> getUserByAdmin(@PathVariable String email,
			@RequestHeader(value = "Authorization") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		User user = getUserService().findByEmail(email);
		if (user != null) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<Message>(new Message("Not found user"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * get user by email
	 * 
	 * @param email
	 *            email of user that want to show
	 * @return user if not return message fail
	 */
	@JsonView(UserView.AllInformation.class)
	@RequestMapping(value = "/{email:.+}", method = RequestMethod.GET)
	public ResponseEntity<?> getUserByEmail(@PathVariable String email,
			@RequestHeader(value = "Authorization") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.MEMBER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		User user = getUserService().findByEmail(email);
		if (user != null) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<Message>(new Message("Not found user"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Creates new user.
	 * 
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"/new"}, method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user) {

		user.setEmail(user.getEmail().toLowerCase());

		if (!isValidEmailAddress(user.getEmail())) {
			return new ResponseEntity<Message>(new Message("This email format cannot use"), HttpStatus.BAD_REQUEST);
		}

		if (checkSpecialCharacter(user.getPassword())) {
			return new ResponseEntity<Message>(new Message("Password connot use special character"),
					HttpStatus.BAD_REQUEST);
		}

		if (user.getPassword().length() < 8 && user.getPassword().length() > 20) {
			return new ResponseEntity<Message>(new Message("Password lenght should be between 8 -20"),
					HttpStatus.BAD_REQUEST);
		}

		Date date = new Date();
		String passwordHash = user.changeToHash(user.getPassword());
		user.setPassword(passwordHash);
		user.setJoiningDate(date);
		user.setRole(roleService.findByName(roleService.MEMBER));
		User oldUser = userService.findByEmail(user.getEmail());

		if (oldUser != null) {
			return new ResponseEntity<Message>(new Message("This email has used"), HttpStatus.BAD_REQUEST);
		}

		try {
			getUserService().store(user);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<Message>(new Message("Create user failed"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Message>(new Message("The user has created"), HttpStatus.CREATED);
	}

	/**
	 * Creates new user.
	 * 
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"/newByOwner"}, method = RequestMethod.POST)
	public ResponseEntity<?> createUserByOwner(@RequestBody User user,
			@RequestHeader(value = "Authorization") String token,
			@RequestParam(value = "roleId", required = false) int id) {

		if (!authenticationService.checkPermission(token, authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		user.setEmail(user.getEmail().toLowerCase());

		if (!isValidEmailAddress(user.getEmail())) {
			return new ResponseEntity<Message>(new Message("This email format cannot use"), HttpStatus.BAD_REQUEST);
		}

		if (checkSpecialCharacter(user.getPassword())) {
			return new ResponseEntity<Message>(new Message("Password connot use special character"),
					HttpStatus.BAD_REQUEST);
		}

		if (user.getPassword().length() < 8 && user.getPassword().length() > 20) {
			return new ResponseEntity<Message>(new Message("Password lenght should be between 8 -20"),
					HttpStatus.BAD_REQUEST);
		}

		Date date = new Date();
		String passwordHash = user.changeToHash(user.getPassword());
		user.setPassword(passwordHash);
		user.setJoiningDate(date);
		user.setRole(roleService.findByKey(id));

		User oldUser = userService.findByEmail(user.getEmail());
		if (oldUser != null) {
			return new ResponseEntity<Message>(new Message("This email has used"), HttpStatus.BAD_REQUEST);
		}

		try {
			getUserService().store(user);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Create user failed"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Message>(new Message("The user has created"), HttpStatus.CREATED);
	}

	/**
	 * edit user by member
	 * 
	 * @param newUser
	 *            put user information that want to change, it is not required
	 *            all parameter of user.
	 * @return message message and email of user or not return message fail and
	 *         string "not found"
	 */
	@ResponseBody
	@RequestMapping(value = {"/edit"}, method = RequestMethod.PUT)
	public ResponseEntity<?> editUserByMember(@RequestBody User newUser,
			@RequestHeader(value = "Authorization") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.MEMBER, authenticationService.STAFF,
				authenticationService.MANAGER, authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
		if (!authenticationService.findByToken(token).getUser().getId().equals(newUser.getId())) {
			return new ResponseEntity<Message>(new Message("This user cannot edit other person"), HttpStatus.FORBIDDEN);
		}
		//        User user = getUserService().findByUsername(newUser.getUsername());
		newUser.setRole(roleService.findByName(roleService.MEMBER));
		try {
			getUserService().update(newUser);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Edit fail"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Message>(new Message("This user has edited"), HttpStatus.OK);
	}

	/**
	 * edit user by member
	 * 
	 * @param newUser
	 *            put user information that want to change, it is not required
	 *            all parameter of user.
	 * @return message message and email of user or not return message fail and
	 *         string "not found"
	 */
	@JsonView(UserView.AllInformation.class)
	@ResponseBody
	@RequestMapping(value = {"/{email:.+}"}, method = RequestMethod.PUT)
	public ResponseEntity<?> editUser(@PathVariable String email, @RequestBody User newUser,
			@RequestHeader(value = "Authorization") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.MEMBER, authenticationService.STAFF,
				authenticationService.MANAGER, authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		if (!authenticationService.findByToken(token).getUser().getEmail().equals(email)) {
			return new ResponseEntity<Message>(new Message("This user cannot edit other person"), HttpStatus.FORBIDDEN);
		}

		if (!authenticationService.findByToken(token).getUser().getId().equals(newUser.getId())) {
			return new ResponseEntity<Message>(new Message("This user cannot edit other person"), HttpStatus.FORBIDDEN);
		}
		
		newUser.setPassword(newUser.changeToHash(newUser.getPassword()));
		newUser.setRole(roleService.findByName(roleService.MEMBER));
		try {
			getUserService().update(newUser);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Edit fail"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<User>(userService.findByKey(newUser.getId()), HttpStatus.OK);
	}

	/**
	 * edit user by admin
	 * 
	 * @param newUser
	 *            put user information that want to change, it is not required
	 *            all parameter of user.
	 * @return message message and email of user or not return message fail and
	 *         string "not found"
	 */
	@ResponseBody
	@RequestMapping(value = {"/editByOwner"}, method = RequestMethod.PUT)
	public ResponseEntity<?> editUserByAdmin(@RequestBody User newUser,
			@RequestHeader(value = "Authorization") String token,
			@RequestParam(value = "roleId", required = false) int id) {
		if (!authenticationService.checkPermission(token, authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		newUser.setRole(roleService.findByKey(id));

		try {
			getUserService().update(newUser);
			userService.updateRole(newUser);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Edit fail"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Message>(new Message("getUserService().findByEmail(newUser.getEmail()).getEmail()"),
				HttpStatus.OK);
	}

	/*
	 * This method will delete an user by it's Username value.
	 */
	/**
	 * delete a user by id
	 * 
	 * @param tempUser
	 *            username and password
	 * @return message message success if not return message fail
	 */
	@ResponseBody
	@RequestMapping(value = {"/delete"}, method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@RequestBody User tempUser,
			@RequestHeader(value = "Authorization") String token) {
		if (!authenticationService.checkPermission(token, authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}
		User user = getUserService().findByEmail(tempUser.getEmail());
		String passwordHash = user.changeToHash(tempUser.getPassword());

		if (!user.getPassword().equals(passwordHash)) {
			return new ResponseEntity<Message>(new Message("Incorrect password"), HttpStatus.BAD_REQUEST);
		}

		try {
			getUserService().removeByEmail(user.getEmail());
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Delete fail"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Message>(new Message("User has removed"), HttpStatus.OK);
	}

	/**
	 * Get location
	 * 
	 * @param email
	 *            email
	 * @param password
	 *            password
	 * @param token
	 *            token
	 * @return location of user
	 */
	@JsonView(UserView.Location.class)
	@RequestMapping(value = {"/location"}, method = RequestMethod.GET)
	public ResponseEntity<?> getLocation(@RequestHeader(value = "email") String email,
			@RequestHeader(value = "password") String password, @RequestHeader(value = "Authorization") String token) {

		if (!authenticationService.checkPermission(token, authenticationService.MEMBER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		if (!authenticationService.findByToken(token).getUser().getEmail().equals(email)) {
			return new ResponseEntity<Message>(new Message("This user cannot get location"), HttpStatus.FORBIDDEN);
		}

		User user = userService.findByEmail(email);
		String passwordHash = user.changeToHash(password);

		if (!passwordHash.equals(user.getPassword())) {
			return new ResponseEntity<Message>(new Message("This user cannot get location"), HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/**
	 * Get payment
	 * 
	 * @param email
	 *            email
	 * @param password
	 *            password
	 * @param token
	 *            token
	 * @return payment of user
	 */
	@JsonView(UserView.PaymentInformation.class)
	@RequestMapping(value = {"/payment"}, method = RequestMethod.GET)
	public ResponseEntity<?> getPayment(@RequestHeader(value = "email") String email,
			@RequestHeader(value = "password") String password, @RequestHeader(value = "Authorization") String token) {

		if (!authenticationService.checkPermission(token, authenticationService.MEMBER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		if (!authenticationService.findByToken(token).getUser().getEmail().equals(email)) {
			return new ResponseEntity<Message>(new Message("This user cannot get payment"), HttpStatus.FORBIDDEN);
		}

		User user = userService.findByEmail(email);

		String passwordHash = user.changeToHash(password);

		if (!passwordHash.equals(user.getPassword())) {
			return new ResponseEntity<Message>(new Message("This user cannot get payment"), HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/**
	 * Edit location
	 * 
	 * @param email
	 *            email
	 * @param password
	 *            password
	 * @param token
	 *            token
	 * @param user
	 *            location of user
	 * @return location of user
	 */
	@ResponseBody
	@RequestMapping(value = {"/location"}, method = RequestMethod.PUT)
	public ResponseEntity<?> editLocation(@RequestHeader(value = "email") String email,
			@RequestHeader(value = "password") String password, @RequestHeader(value = "Authorization") String token,
			@RequestBody User newUser) {

		if (!authenticationService.checkPermission(token, authenticationService.MEMBER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		if (!authenticationService.findByToken(token).getUser().getEmail().equals(email)) {
			return new ResponseEntity<Message>(new Message("This user cannot get location"), HttpStatus.FORBIDDEN);
		}

		User user = userService.findByEmail(email);
		String passwordHash = user.changeToHash(password);

		if (!passwordHash.equals(user.getPassword())) {
			return new ResponseEntity<Message>(new Message("This user cannot get location"), HttpStatus.FORBIDDEN);
		}

		user.setAddress1(newUser.getAddress1());
		user.setAddress2(newUser.getAddress2());
		user.setProvince(newUser.getProvince());
		user.setZipcode(newUser.getZipcode());
		user.setTelephone_number(newUser.getTelephone_number());

		try {
			getUserService().update(user);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Edit fail"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Message>(new Message("Edited"), HttpStatus.OK);
	}

	/**
	 * Edit payment
	 * 
	 * @param email
	 *            email
	 * @param password
	 *            password
	 * @param token
	 *            token
	 * @param user
	 *            payment of user
	 * @return payment of user
	 */
	@ResponseBody
	@RequestMapping(value = {"/payment"}, method = RequestMethod.PUT)
	public ResponseEntity<?> editPayment(@RequestHeader(value = "email") String email,
			@RequestHeader(value = "password") String password, @RequestHeader(value = "Authorization") String token,
			@RequestBody User newUser) {

		if (!authenticationService.checkPermission(token, authenticationService.MEMBER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		if (!authenticationService.findByToken(token).getUser().getEmail().equals(email)) {
			return new ResponseEntity<Message>(new Message("This user cannot get payment"), HttpStatus.FORBIDDEN);
		}

		User user = userService.findByEmail(email);
		String passwordHash = user.changeToHash(password);

		if (!passwordHash.equals(user.getPassword())) {
			return new ResponseEntity<Message>(new Message("This user cannot get payment"), HttpStatus.FORBIDDEN);
		}

		user.setCard_name(newUser.getCard_name());
		user.setCard_number(newUser.getCard_number());
		user.setExpirationDate(newUser.getExpirationDate());

		try {
			getUserService().updatePayment(user);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Edit fail"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Message>(new Message("Edited"), HttpStatus.OK);
	}

	private boolean checkSpecialCharacter(String name) {
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(name);
		return m.find();
	}

	private static boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9._-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		Pattern p = Pattern.compile(ePattern);
		Matcher m = p.matcher(email);
		return m.matches();
	}
}
