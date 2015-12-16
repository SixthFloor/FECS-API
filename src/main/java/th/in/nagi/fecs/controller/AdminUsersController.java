package th.in.nagi.fecs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonView;

import th.in.nagi.fecs.message.Message;
import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.service.AuthenticationService;
import th.in.nagi.fecs.service.UserService;
import th.in.nagi.fecs.view.UserView;

/**
 * Admin controller for users.
 * 
 * @author Chonnipa Kittisiriprasert
 *
 */
@Controller
@RequestMapping("/api/user/admin")
public class AdminUsersController extends BaseController {

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
	 * Gets user service.
	 * 
	 * @return user service
	 */
	protected UserService getUserService() {
		return userService;
	}

	/**
	 * get user by email
	 * 
	 * @param email
	 *            email of user that want to show
	 * @return user if not return message fail
	 */
	@JsonView(UserView.All.class)
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<?> search(@RequestHeader(value = "Authorization") String token,
			@RequestParam(value = "keyword", required = true) String keyword) {
		if (!authenticationService.checkPermission(token, authenticationService.STAFF, authenticationService.MANAGER,
				authenticationService.OWNER)) {
			return new ResponseEntity<Message>(new Message("This user does not allow"), HttpStatus.FORBIDDEN);
		}

		List<User> users = getUserService().findByKeyword(keyword);
		if (users != null) {
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		}
		return new ResponseEntity<Message>(new Message("Not found user"), HttpStatus.BAD_REQUEST);
	}
}
