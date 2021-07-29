package br.ufrrj.samu.controllers;

import br.ufrrj.samu.entities.User;
import br.ufrrj.samu.services.StudentService;
import br.ufrrj.samu.utils.LoginStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class LoginController {

    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    private StudentService studentService;

    public LoginController(StudentService studentService) {
        this.studentService = studentService;
    }


    /**
     * @param username
     * @param password
     * @return
     */
    public LoginStatus checkPassword(String username, String password) {
        Optional<User> userDB = studentService.findStudentByName(username);

        if(userDB.isEmpty()) {
            return LoginStatus.UNKNOWN_USER;
        } else {
            User user = userDB.get();

            if(studentService.getEncoder().matches(password, user.getPassword())) {
                return LoginStatus.SUCCESS;
            }
        }
        return LoginStatus.WRONG_PASSWORD;
    }

}
