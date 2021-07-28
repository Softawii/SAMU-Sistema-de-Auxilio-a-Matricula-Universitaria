package br.ufrrj.samu.controllers;

import br.ufrrj.samu.entities.User;
import br.ufrrj.samu.services.UserService;
import br.ufrrj.samu.utils.LoginStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class LoginController {

    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }


    /**
     * @param username
     * @param password
     * @return
     */
    public LoginStatus checkPassword(String username, String password) {
        Optional<User> userDB = userService.findUserByName(username);

        if(userDB.isEmpty()) {
            return LoginStatus.UNKNOWN_USER;
        } else {
            User user = userDB.get();

            if(userService.getEncoder().matches(password, user.getPassword())) {
                return LoginStatus.SUCCESS;
            }
        }
        return LoginStatus.WRONG_PASSWORD;
    }

}
