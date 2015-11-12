package th.in.nagi.fecs.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import org.springframework.web.bind.annotation.ResponseBody;

import th.in.nagi.fecs.message.ErrorMessage;
import th.in.nagi.fecs.message.Message;
import th.in.nagi.fecs.message.SuccessMessage;
import th.in.nagi.fecs.model.Authenticate;
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.service.AuthenticateService;
import th.in.nagi.fecs.service.UserService;

/**
 * Controller for authenticate.
 * 
 * @author Nara Surawit
 *
 */
@Controller
@RequestMapping("/api/authentication")
public class AuthenticateController extends BaseController {

	/**
	 * Authenticate service
	 */
	@Autowired
	private AuthenticateService authenticateService;

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
	protected AuthenticateService getAuthenticateService() {
		return authenticateService;
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
	@ResponseBody
	@RequestMapping(value = "/{email:.+}", method = RequestMethod.GET)
	public ResponseEntity getAuthenticateByUsername(@PathVariable String email) {
		List<Authenticate> authenticate = getAuthenticateService().findByEmail(email);
		if (authenticate != null) {
			return new ResponseEntity(authenticate, HttpStatus.OK);
		}
		return new ResponseEntity(new Message("Not found authenticate"), HttpStatus.BAD_REQUEST);
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
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity login(@RequestBody User tempUser) {
		System.out.println(tempUser.getEmail());
		System.out.println(tempUser.getPassword());
		User user = getUserService().findByEmail(tempUser.getEmail());
		String passwordHash = user.changeToHash(tempUser.getPassword());

		if (!user.getPassword().equals(passwordHash)) {
			return new ResponseEntity(new Message("Incorrect password"), HttpStatus.BAD_REQUEST);
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
				// System.out.println(String.format("%064x", new
				// java.math.BigInteger(1, hash3)));

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		date.setDate((date.getDate() + 1));
		Authenticate authenticate = new Authenticate(textHash, user, date);

		if (authenticate != null) {
			getAuthenticateService().store(authenticate);
			Authenticate dataBaseAuthenticate = getAuthenticateService().findByToken(authenticate.getToken());
			return new ResponseEntity(new Message(dataBaseAuthenticate.getToken()), HttpStatus.CREATED);
		}
		return new ResponseEntity(new Message("Not found authenticate"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * check token in database and token of input user
	 * 
	 * @param authenticate
	 * @return message message and email of user ,or not return message fail.
	 */
	@ResponseBody
	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public ResponseEntity checkToken(@RequestBody Authenticate authenticate) {
		if (authenticateService.isExpiration(authenticate.getToken())) {
			return new ResponseEntity(new Message(authenticateService.findByToken(authenticate.getToken()).getUser().getEmail()),
					HttpStatus.CREATED);
		}
		return new ResponseEntity(new Message("Token is expiration."), HttpStatus.BAD_REQUEST);
	}
}
