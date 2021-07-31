package br.ufrrj.samu.controllers;

import br.ufrrj.samu.entities.Student;
import br.ufrrj.samu.entities.Subject;
import br.ufrrj.samu.services.StudentService;
import br.ufrrj.samu.services.SubjectService;

import java.util.List;
import java.util.Optional;

public class HomeController {

    private SubjectService subjectService;
    private StudentService studentService;

    public HomeController(SubjectService subjectService, StudentService studentService) {
        this.subjectService = subjectService;
        this.studentService = studentService;
    }

    public Student getStudent(String username) {
        Optional<Student> studentOptional = studentService.findStudentByUsername(username);
        //Isso nunca vai acontecer, mas tem que seguir as boas práticas né?
        if (studentOptional.isEmpty()) {
            return new Student("João Gameplays e Tutoriais", "QWERTY", "João", "Minha rua, número da minha casa", List.of(), "Curso", "0.1");
        }

        return studentOptional.get();
    }

    public List<Subject> getStudentSubjects(String username) {
        Optional<Student> studentOptional = studentService.findStudentByUsername(username);
        //Isso nunca vai acontecer, mas tem que seguir as boas práticas né?
        if (studentOptional.isEmpty()) {
            return List.of();
        }
        return studentOptional.get().getSubjects();
    }
}
