package br.ufrrj.samu.controllers;

import br.ufrrj.samu.entities.User;
import br.ufrrj.samu.exceptions.PasswordNotMatchesException;
import br.ufrrj.samu.exceptions.UnknownUserException;
import br.ufrrj.samu.repositories.UsersRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class LoginController {

    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    public User signIn(String username, String password) throws UnknownUserException, PasswordNotMatchesException {
        UsersRepository usersRepository = UsersRepository.getInstance();
        Optional<User> optionalUser = usersRepository.findByUsername(username);

        if(optionalUser.isEmpty()) {
            throw new UnknownUserException();
        } else {
            User user = optionalUser.get();

            if(usersRepository.getEncoder().matches(password, user.getPassword())) { // criptografado
                return user;
            }
        }
        throw new PasswordNotMatchesException();
    }
}
