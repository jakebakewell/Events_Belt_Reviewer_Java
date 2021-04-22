package com.jb.eventsbeltreviewer.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jb.eventsbeltreviewer.models.Event;
import com.jb.eventsbeltreviewer.models.User;
import com.jb.eventsbeltreviewer.services.EventService;
import com.jb.eventsbeltreviewer.services.UserService;
import com.jb.eventsbeltreviewer.validators.UserValidator;

@Controller
public class LoginRegController {
	private final UserService userService;
	private final UserValidator userValidator;
	private final EventService eventService;
	public LoginRegController(UserService userService, UserValidator userValidator, EventService eventService) {
		this.userService = userService;
		this.userValidator = userValidator;
		this.eventService = eventService;
	}
	
	@RequestMapping("/")
	public String registerPage(Model model) {
		model.addAttribute("user", new User());
		return "loginReg.jsp";
	}
	
	@RequestMapping(value="/registration", method=RequestMethod.POST)
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session, RedirectAttributes redirect) {
		userValidator.validate(user, result);
		if(userService.isEmailAlreadyInUse(user.getEmail())) {
			ObjectError emailError = new ObjectError("email", "That email is already being used");
			result.addError(emailError);
		}
		if (result.hasErrors()) {
			return "loginReg.jsp";
		}
		else {
			User u = userService.registerUser(user);
			session.setAttribute("userId", u.getId());
			redirect.addFlashAttribute("success", "You have registered and are now on the home page!");
			return "redirect:/home";
		}
	}
	
    @RequestMapping("/login")
    public String loginPage() {
        return "login.jsp";
    }
    
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, RedirectAttributes redirect, HttpSession session) {
    	if (userService.authenticateUser(email, password)) {
    		User u = userService.findByEmail(email);
    		session.setAttribute("userId", u.getId());
    		redirect.addFlashAttribute("success", "You have logged in and are now on the home page!");
    		return "redirect:/home";
    	}
    	else {
    		redirect.addFlashAttribute("error", "Invalid Log In. Incorrect Email/Password.");
    		return "redirect:/login";
    	}
    }
    
    @RequestMapping("/home")
    public String home(Model model, HttpSession session, RedirectAttributes redirect) {
    	Long id = (Long) session.getAttribute("userId");
    	if (id == null) {
    		redirect.addFlashAttribute("warning", "Please register or log in before trying to access our site.");
    		return "redirect:/";
    	}
    	else {
    		User user = userService.findUserById(id);
    		model.addAttribute("user", user);
    		String state = user.getState();
    		List<Event> inStateEvents = eventService.findEventsByState(state);
    		List<Event> outStateEvents = eventService.findEventsNotInState(state);
    		model.addAttribute("inStateEvents", inStateEvents);
    		model.addAttribute("outStateEvents", outStateEvents);
    		model.addAttribute("newEvent", new Event());
    	}
    	return "home.jsp";
    }
    
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
    	session.invalidate();
    	return "redirect:/";
    }
}
