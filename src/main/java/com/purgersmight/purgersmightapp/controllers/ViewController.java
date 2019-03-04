package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.dto.CreateNewUserReqDto;
import com.purgersmight.purgersmightapp.dto.LoginReqDto;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.services.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class ViewController {

    @Autowired
    private AvatarService avatarService;

    Logger logger = Logger.getLogger(ViewController.class.getName());

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String defaultPage(Model model) {

        model.addAttribute("loginReqDto", new LoginReqDto());

        logger.log(Level.INFO, "Request for Login Page");

        return "login.html";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {

        model.addAttribute("loginReqDto", new LoginReqDto());

        logger.log(Level.INFO, "Request for Login Page");

        return "login.html";
    }

    @RequestMapping(value = "/create-account", method = RequestMethod.GET)
    public String createAccountPage(Model model) {

        model.addAttribute("createNewUserReqDto", new CreateNewUserReqDto());

        logger.log(Level.INFO, "Request for Create-Account Page");

        return "create-account.html";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String homePage(Model model) {

        model.addAttribute("avatar", getAvatar(getPrincipal()));

        return "home.html";
    }

    @RequestMapping(value = "/waiting-room", method = RequestMethod.GET)
    public String waitingRoomPage() {

        return "waiting-room.html";
    }

    @RequestMapping(value = "/pvp-room", method = RequestMethod.GET)
    public String pvpRoomPage() {

        return "pvp-room.html";
    }

    @RequestMapping(value = "/change-spells", method = RequestMethod.GET)
    public String changeSpellPage() {

        return "change-spells.html";
    }

    @RequestMapping(value = "/upgrade-attributes", method = RequestMethod.GET)
    public String upgradeAttributesPage() {

        return "upgrade-attributes.html";
    }

    @RequestMapping(value = "/leaderboard", method = RequestMethod.GET)
    public String leaderboardPage() {

        return "leaderboard.html";
    }

    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String errorPage() {

        return "error.html";
    }

    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    private String getPrincipal() {

        String userName = null;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {

            userName = ((UserDetails) principal).getUsername();

        } else {

            userName = principal.toString();
        }
        return userName;
    }

    private Avatar getAvatar(String username) {

        return avatarService.getAvatarByUsername(username);
    }
}
