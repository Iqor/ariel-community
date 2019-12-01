package com.igor.controller;

import com.igor.entity.User;
import com.igor.repository.UserRepository;
import com.igor.service.UserService;
import com.igor.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class SignUpController {


    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ModelAndView view;

    private User user;

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public ModelAndView signUpScreen() {

        view = new ModelAndView();

        view.addObject("user", user = new User());

        view.setViewName("Login/SignUp");

        return view;
    }


    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ModelAndView signUpForm(@Valid @ModelAttribute("user") User user, Errors err, RedirectAttributes redirectAttributes) {

        view = new ModelAndView();
        view.setViewName("redirect:/signUp");

        validate(user , err);

        if(err.hasErrors())
            return new ModelAndView("Login/SignUp");

        try {

            if (!userService.exists(user)) {

                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userService.saveUser(user);

                redirectAttributes.addFlashAttribute("message_success" , "User " + user.getUsername() + " was successfully registered");
            } else {
                redirectAttributes.addFlashAttribute("message_error" , "This username already exists");
            }

        } catch (Exception ex) {
            Utils.showErrors(ex);
            redirectAttributes.addFlashAttribute("message_error" , "Error registering user, please contact technical support");
        }


        return view;
    }


    public void validate(User u , Errors err) {

        if (u.getName().length() == 0) {
            err.rejectValue("name", "name.required");
        }

        if (u.getUsername().length() == 0) {
            err.rejectValue("username", "username.required");
        }

        if (!Utils.validString(u.getUsername())) {
            err.rejectValue("username", "username.validate");
        }

        if (u.getInstitution().length() == 0) {
            err.rejectValue("institution", "institution.required");
        }

        if (u.getEmail().length() == 0) {
            err.rejectValue("email", "email.required");
        }

        if (u.getPassword().length() == 0) {
            err.rejectValue("password", "password.required");
        }

        if (u.getConfirmPassword().length() == 0) {
            err.rejectValue("confirmPassword", "confirm.password.required");
        }

        if (u.getPassword().length() != 0 && u.getConfirmPassword().length() != 0) {
            if (!u.getPassword().equals(u.getConfirmPassword())) {
                err.rejectValue("confirmPassword", "passwords.match");
                err.rejectValue("password", "passwords.match");
            }

            if(!Utils.validString(u.getPassword())){
                err.rejectValue("password", "password.validate");
            }

        }else{
            err.rejectValue("password", "passwords.null");
            err.rejectValue("confirmPassword", "passwords.null");
        }

    }

}
