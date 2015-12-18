package th.in.nagi.fecs.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.message.Message;
import th.in.nagi.fecs.model.Authentication;
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.service.AuthenticationService;
import th.in.nagi.fecs.service.UserService;
import th.in.nagi.fecs.view.AuthenticationView;

/**
 * Controller for authenticate.
 * 
 * @author Nara Surawit
 *
 */
@Controller
@RequestMapping("/api/authentication")
public class AuthenticationController extends BaseController {

	/**
	 * Authentication service
	 */
	@Autowired
	private AuthenticationService authenticationService;

	/**
	 * User service
	 */
	@Autowired
	private UserService userService;

	/**
	 * Gets authenticate service.
	 * 
	 * @return authenticate service
	 */
	protected AuthenticationService getAuthenticateService() {
		return authenticationService;
	}

	/**
	 * Gets user service
	 * 
	 * @return user service
	 */
	protected UserService getUserService() {
		return userService;
	}

	/**
	 * Returns list of authenticate by email
	 * 
	 * @param email
	 *            email of user
	 * @return message message and authenticate if not success return message
	 *         and string "not found"
	 */
	@JsonView(AuthenticationView.Personal.class)
	@RequestMapping(value = "/{email:.+}", method = RequestMethod.GET)
	public ResponseEntity<?> getAuthenticateByUsername(@PathVariable String email) {

		List<Authentication> authentication = getAuthenticateService().findByEmail(email);

		if (authentication != null) {
			return new ResponseEntity<List<Authentication>>(authentication, HttpStatus.OK);
		}

		return new ResponseEntity<Message>(new Message("Not found authenticate"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Login and return token of user
	 * 
	 * @param tempUser
	 *            user that want to login it can only input with email and
	 *            password
	 * @return message message and token if not success return message and
	 *         string "not found"
	 */
	@JsonView(AuthenticationView.Summary.class)
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody User tempUser) {

		if (tempUser.getEmail() == null) {
			return new ResponseEntity<Message>(new Message("Email is empty"), HttpStatus.BAD_REQUEST);
		}

		User user = getUserService().findByEmail(tempUser.getEmail().toLowerCase());
		
		if(user == null){
			return new ResponseEntity<Message>(new Message("This account doesn't exist"), HttpStatus.BAD_REQUEST);
		}

		if (tempUser.getPassword() == null) {
			return new ResponseEntity<Message>(new Message("Password is empty"), HttpStatus.BAD_REQUEST);
		}

		String passwordHash = user.changeToHash(tempUser.getPassword());

		if (user.getEmail() == null) {
			return new ResponseEntity<Message>(new Message("User not found"), HttpStatus.BAD_REQUEST);
		}

		if (!user.getPassword().equals(passwordHash)) {
			return new ResponseEntity<Message>(new Message("Incorrect password"), HttpStatus.BAD_REQUEST);
		}

		Date date = new Date();
		String text = tempUser.getEmail() + date.toString();
		String textHash = "";

		try {
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			try {
				byte[] hash1 = sha256.digest(date.toString().getBytes("UTF-8"));
				byte[] hash2 = md5.digest(text.getBytes("UTF-8"));
				byte[] hash3 = sha256
						.digest((String.format("%064x", new java.math.BigInteger(1, hash2))).getBytes("UTF-8"));
				textHash = "==" + String.format("%64x", new java.math.BigInteger(1, hash3)) + "."
						+ String.format("%064x", new java.math.BigInteger(1, hash1));

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		date = cal.getTime();

		Authentication authentication = new Authentication(textHash, user, date);

		getAuthenticateService().store(authentication);
		return new ResponseEntity<Authentication>(authentication, HttpStatus.CREATED);
	}

	/**
	 * check token in database and token of input user
	 * 
	 * @param authentication
	 * @return message message and email of user ,or not return message fail.
	 */
	@JsonView(AuthenticationView.Summary.class)
	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public ResponseEntity<?> checkToken(@RequestBody Authentication authentication) {
		if (authenticationService.isExpired(authentication.getToken())) {
			System.out.println(authentication.getUser());
			return new ResponseEntity<Authentication>(authenticationService.findByToken(authentication.getToken()),
					HttpStatus.OK);
		}
		return new ResponseEntity<Message>(new Message("Token is expiration."), HttpStatus.BAD_REQUEST);
	}
}
