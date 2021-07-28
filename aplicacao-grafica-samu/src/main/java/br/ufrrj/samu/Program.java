package br.ufrrj.samu;

import br.ufrrj.samu.entities.User;
import br.ufrrj.samu.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Program {
    public static void main(String[] args) {
        SAMU.startSamu();
        // SAMU.startSamu();

        Logger LOGGER = LogManager.getLogger(UserService.class);

        UserService userService = new UserService();

        userService.insertUser(new User("Joao", "1234"));

        LOGGER.debug(userService.checkPassword("Joao", "1234"));
    }
}
