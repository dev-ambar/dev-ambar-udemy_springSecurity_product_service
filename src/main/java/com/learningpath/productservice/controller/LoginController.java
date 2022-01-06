package com.learningpath.productservice.controller;

import com.learningpath.productservice.model.Role;
import com.learningpath.productservice.model.User;
import com.learningpath.productservice.repos.UserRepository;
import com.learningpath.productservice.security.service.SecurityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Set;

@Controller
public class LoginController {
    @Autowired
    private SecurityServiceImpl securityService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String getLoginPage()
    {
        return "login";
    }

    @GetMapping("/showReg")
    public String showRegistrationPage()
    {
        return "registerUser";
    }

    @PostMapping("/registerUser")
    public ModelAndView userRegistration(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
         /*Role r1 = new Role();
         r1.setName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(r1);
        user.setUserRoles(roleSet);*/
        User create =  userRepository.save(user);
        if(create!=null)
            return new ModelAndView("login").addObject("user has been registered");
        else
            return new ModelAndView("registerUser").addObject("user has been not registered");
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password)
    {
        boolean isLogin = securityService.login(email, password);
        if(isLogin)
        return "index";
        else
            return "login";
    }
}
