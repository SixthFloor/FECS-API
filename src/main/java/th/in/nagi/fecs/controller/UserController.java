package th.in.nagi.fecs.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import th.in.nagi.fecs.model.User;
import th.in.nagi.fecs.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	MessageSource messageSource;

	/*
	 * This method will list all existing users.
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listUsers(ModelMap model) {

		List<User> users = userService.findAll();
		model.addAttribute("users", users);
		return "usersList";
	}

	/*
	 * This method will provide the medium to add a new user.
	 */
	@RequestMapping(value = {"/new"}, method = RequestMethod.GET)
	public String newUser(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("edit", false);
		return "registration";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input
	 */
	@RequestMapping(value = {"/new"}, method = RequestMethod.POST)
	public String saveUser(@Valid User user, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			return "registration";
		}

		/*
		 * Preferred way to achieve uniqueness of field [username] should be implementing custom @Unique annotation 
		 * and applying it on field [username] of Model class [User].
		 * 
		 * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
		 * framework as well while still using internationalized messages.
		 * 
		 */
		if (!userService.isUsernameUnique(user.getId(), user.getUsername())) {
			FieldError usernameError = new FieldError("user", "username", messageSource
					.getMessage("non.unique.username", new String[] {user.getUsername()}, Locale.getDefault()));
			result.addError(usernameError);
			return "registration";
		}

		userService.save(user);

		model.addAttribute("success",
				"User " + user.getFirstName() + " " + user.getLastName() + " registered successfully");
		return "success";
	}

	/*
	 * This method will provide the medium to update an existing user.
	 */
	@RequestMapping(value = {"/edit-{username}-user"}, method = RequestMethod.GET)
	public String editUser(@PathVariable String username, ModelMap model) {
		User user = userService.findByUsername(username);
		model.addAttribute("user", user);
		model.addAttribute("edit", true);
		return "registration";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * updating user in database. It also validates the user input
	 */
	@RequestMapping(value = {"/edit-{username}-user"}, method = RequestMethod.POST)
	public String updateUser(@Valid User user, BindingResult result, ModelMap model, @PathVariable String username) {

		if (result.hasErrors()) {
			return "registration";
		}

		if (!userService.isUsernameUnique(user.getId(), user.getUsername())) {
			FieldError usernameError = new FieldError("user", "username", messageSource
					.getMessage("non.unique.username", new String[] {user.getUsername()}, Locale.getDefault()));
			result.addError(usernameError);
			return "registration";
		}

		userService.update(user);

		model.addAttribute("success",
				"User " + user.getFirstName() + " " + user.getLastName() + " updated successfully");
		return "success";
	}

	/*
	 * This method will delete an user by it's Username value.
	 */
	@RequestMapping(value = {"/delete-{username}-user"}, method = RequestMethod.GET)
	public String deleteUser(@PathVariable String username) {
		userService.deleteByUsername(username);
		return "redirect:/user/list";
	}

}
