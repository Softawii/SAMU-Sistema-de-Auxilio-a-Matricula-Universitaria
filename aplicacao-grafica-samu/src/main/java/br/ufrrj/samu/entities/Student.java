package br.ufrrj.samu.entities;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private String course;
    private String semester;
    private ArrayList<Subject> subjects;

    public Student() {
        super();
    }

    public Student(String username, String password, String name, String address, List<Subject> subjects, String course, String semester) {
        this(0, username, password, name, address, subjects, course, semester);
    }

    public Student(long id, String username, String password, String name, String address, List<Subject> subjects, String course, String semester) {
        super(id, username, password);
        this.course = course;
        this.semester = semester;

        try {
            this.subjects = new ArrayList<>(subjects);
        } catch(NullPointerException e) {
            this.subjects = new ArrayList<>();
        }
    }

    public Student(String username, String password, String name, String cpf, String address, String birthday, String course, String semester, ArrayList<Subject> subjects) {
        this(0, username, password, name, cpf, address, birthday, course, semester, subjects);
    }

    public Student(long id, String username, String password, String name, String cpf, String address, String birthday, String course, String semester, ArrayList<Subject> subjects) {
        super(id, username, password, name, cpf, address, birthday);
        this.course = course;
        this.semester = semester;

        try {
            this.subjects = new ArrayList<>(subjects);
        } catch(NullPointerException e) {
            this.subjects = new ArrayList<>();
        }
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void addSubject(Subject subject) {
        this.subjects.add(subject);
    }

    public void removeSubject(Subject subject) {
        this.subjects.remove(subject);
    }

    public String getCourse() {
        return course;
    }

    public String getSemester() {
        return semester;
    }

    public String getSubjectsCodes() {
        // Maybe we can get a better name to this
        StringBuilder stringBuilder = new StringBuilder();

        for(Subject subject : this.subjects) {
            stringBuilder.append(subject.getCode() + ",");
        }

        if(!stringBuilder.isEmpty()) {
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        }

        return stringBuilder.toString();
    }
}
