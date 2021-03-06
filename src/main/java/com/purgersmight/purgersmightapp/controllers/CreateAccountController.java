package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.dto.CreateNewUserReqDto;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.BattleStatistics;
import com.purgersmight.purgersmightapp.models.PlayerBattleReceipts;
import com.purgersmight.purgersmightapp.models.User;
import com.purgersmight.purgersmightapp.services.AvatarService;
import com.purgersmight.purgersmightapp.services.BattleStatisticsService;
import com.purgersmight.purgersmightapp.services.PlayerBattleReceiptService;
import com.purgersmight.purgersmightapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class CreateAccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private AvatarService avatarService;

    @Autowired
    private PlayerBattleReceiptService playerBattleReceiptService;

    @Autowired
    private BattleStatisticsService battleStatisticsService;

    private Logger logger = Logger.getLogger(UserServiceController.class.getName());

    @RequestMapping(value = "/create-account", method = RequestMethod.POST)
    public RedirectView createUser(@ModelAttribute @Valid CreateNewUserReqDto createNewUserReqDto, BindingResult result, HttpServletResponse response) {

        if (result.hasErrors()) {

            logger.log(Level.INFO, String.format("%s has tried to create an account but was unsuccessful", createNewUserReqDto.getUsername()));

            return new RedirectView("/create-account?error");
        }

        User newUser = new User(createNewUserReqDto.getUsername(), createNewUserReqDto.getPassword());

        Avatar newAvatar = Avatar.getStarterAvatar(newUser.getUsername());

        if (createNewUserReqDto.getImageUrl() != null) {

            newAvatar.setImageUrl(createNewUserReqDto.getImageUrl());
        }

        PlayerBattleReceipts newPlayerBattleReceipts = new PlayerBattleReceipts(createNewUserReqDto.getUsername());

        BattleStatistics newBattleStatistics = new BattleStatistics(createNewUserReqDto.getUsername());

        userService.addUser(newUser);

        avatarService.addAvatar(newAvatar);

        playerBattleReceiptService.addPlayerBattleReceipt(newPlayerBattleReceipts);

        battleStatisticsService.addBattleStatistics(newBattleStatistics);

        return new RedirectView("/login?created");
    }
}
