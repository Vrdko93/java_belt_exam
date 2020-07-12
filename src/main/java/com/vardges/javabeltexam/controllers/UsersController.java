package com.vardges.javabeltexam.controllers;

import org.springframework.stereotype.Controller;
import com.vardges.javabeltexam.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import com.vardges.javabeltexam.models.User;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;
import com.vardges.javabeltexam.validator.UserValidator;

@Controller
public class UsersController {
	private final UserService userService;
	private final UserValidator userValidator;
    
    public UsersController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }
    
    @RequestMapping("/")
    public String registerForm(@ModelAttribute("user") User user) {
        return "loginAndReg.jsp";
    }
    
    @RequestMapping(value="/registration", method=RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
    	userValidator.validate(user, result);
        if (result.hasErrors()) {
        	return "loginAndReg.jsp";
        } 
        User u = userService.registerUser(user);
        session.setAttribute("userId", u.getId());
        return "redirect:/tasks";
    }
    
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String loginUser(@ModelAttribute("user") User user, @RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session) {
    	if (userService.authenticateUser(email, password)) {
    		User u = userService.findByEmail(email);
    		session.setAttribute("userId", u.getId());
    		return "redirect:/tasks";
    	}
    	else {
    		user.setEmail("");
    		model.addAttribute("error", "Check your email and password");
    		return "loginAndReg.jsp";
    	}
    }
    
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        // invalidate session
        // redirect to login page
    	session.invalidate();
    	return "redirect:/";
    }
}
