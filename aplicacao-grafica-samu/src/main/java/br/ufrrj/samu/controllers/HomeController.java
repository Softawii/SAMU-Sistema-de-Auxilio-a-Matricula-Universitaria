package br.ufrrj.samu.controllers;

import br.ufrrj.samu.entities.Student;
import br.ufrrj.samu.entities.Subject;
import br.ufrrj.samu.exceptions.WrongRequestedUserTypeException;
import br.ufrrj.samu.repositories.StudentRepository;
import br.ufrrj.samu.repositories.SubjectRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class HomeController {

    private SubjectRepository subjectRepository;
    private StudentRepository studentRepository;

    public HomeController(SubjectRepository subjectRepository, StudentRepository studentRepository) {
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
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
}
