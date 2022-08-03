package com.cg.controller;

import com.cg.model.dto.UserDTO;
import com.cg.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    UserService userService;

    private String getPrincipal(){
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = "";
        }

        return email;
    }

    private UserDTO getUserDTO(){
        String email = getPrincipal();
        Optional<UserDTO> userDTOOptional = userService.findUserDTOByEmail(email);
        if (!userDTOOptional.isPresent()){
            return null;
        }
        return userDTOOptional.get();
    }

    @GetMapping("/home")
    public ModelAndView showHomePage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/home");
        return modelAndView;
    }

    @GetMapping("login")
    public String showLoginPage(){
        System.out.println("Show login cho tui ");
//        UserDTO userDTO = getUserDTO();
//        if(userDTO != null){
//            return "redirect:/home";
//        }
        return "/login";
    }
//    @GetMapping("register")
//    public ModelAndView showRegisterPage() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("/register");
//        return modelAndView;
//    }
}
