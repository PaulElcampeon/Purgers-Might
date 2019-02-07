package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.dto.LoginReqDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class ViewController {

    Logger logger = Logger.getLogger(ViewController.class.getName());

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String defaultPage(Model model){
        model.addAttribute("loginReqDto", new LoginReqDto());
        logger.log(Level.INFO, "Request for Login Page");
        return "login.html";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model){
        model.addAttribute("loginReqDto", new LoginReqDto());
        logger.log(Level.INFO, "Request for Login Page");
        return "login.html";
    }

    @RequestMapping(value = "/create-account", method = RequestMethod.GET)
    public String createAccountPage(){
        return "create-account.html";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String homePage(){
        return "home.html";
    }
}
