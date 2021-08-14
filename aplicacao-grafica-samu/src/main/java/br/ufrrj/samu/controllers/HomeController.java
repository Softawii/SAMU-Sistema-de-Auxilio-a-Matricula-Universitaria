package br.ufrrj.samu.controllers;

import br.ufrrj.samu.entities.Student;
import br.ufrrj.samu.entities.User;
import br.ufrrj.samu.exceptions.WrongRequestedUserTypeException;
import br.ufrrj.samu.repositories.StudentRepository;
import br.ufrrj.samu.repositories.SubjectRepository;
import br.ufrrj.samu.repositories.UsersRepository;

import java.sql.SQLException;
import java.util.Optional;

public class HomeController {

    private SubjectRepository subjectRepository;
    private StudentRepository studentRepository;
    private UsersRepository usersRepository;

    public HomeController() {
        this.subjectRepository = SubjectRepository.getInstance();
        this.studentRepository = StudentRepository.getInstance();
        this.usersRepository = UsersRepository.getInstance();
    }

    public Student getStudent(long userId) {
        // TODO Desfazer gambiarra
        Optional<Student> studentOptional = null;
        try {
            studentOptional = studentRepository.findById(userId);
        } catch (WrongRequestedUserTypeException | SQLException e) {
            e.printStackTrace();
        }

        return studentOptional.get();
    }

    public Optional<User> getTeacher(long teacherId) {
        return usersRepository.findById(teacherId);
    }
}
