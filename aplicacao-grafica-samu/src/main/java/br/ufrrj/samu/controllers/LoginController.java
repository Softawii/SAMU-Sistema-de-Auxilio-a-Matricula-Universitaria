package br.ufrrj.samu.controllers;

import br.ufrrj.samu.entities.Student;
import br.ufrrj.samu.entities.User;
import br.ufrrj.samu.repositories.StudentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class LoginController {

    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    private StudentRepository studentRepository;

    public LoginController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentRepository getStudentService() {
        return studentRepository;
    }

    /**
     * Verifica se usuário e senha inseridos são válidos
     * @param username
     * @param password
     * @return LoginStatus
     */
    public LoginStatus checkPassword(String username, String password) {
        Optional<Student> userDB = studentRepository.findStudentByUsername(username);

        if(userDB.isEmpty()) {
            return LoginStatus.UNKNOWN_USER;
        } else {
            User user = userDB.get();

//            if(studentService.getEncoder().matches(password, user.getPassword())) { // criptografado
            if(password.equals(user.getPassword())) {
                return LoginStatus.SUCCESS;
            }
        }
        return LoginStatus.WRONG_PASSWORD;
    }

    public enum LoginStatus {
        WRONG_PASSWORD("Senha inválida"),
        UNKNOWN_USER("Usuário desconhecido"),
        SUCCESS("Sucesso");

        private String message;

        LoginStatus(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
