package br.ufrrj.samu.entities;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private String course;
    private String semester;
    private ArrayList<Subject> subjects;

    private ArrayList<Lecture> enrollLectures;
    private ArrayList<Lecture> requestedLectures;

    public Student() {
        super();
    }

    public Student(long id, String username, String password, String name, String cpf, String address, String birthday,
                   String course, String semester, ArrayList<Lecture> enrollLectures, ArrayList<Lecture> requestedLectures) {
        super(id, username, password, name, cpf, address, birthday);

        this.course = course;
        this.semester = semester;

        try {
            this.enrollLectures = new ArrayList<>(enrollLectures);
        } catch (Exception e) {
            this.enrollLectures = new ArrayList<>();
        }

        try {
            this.requestedLectures = new ArrayList<>(requestedLectures);
        } catch (Exception e) {
            this.requestedLectures = new ArrayList<>();
        }
    }

    public Student(String username, String password, String name, String cpf, String address, String birthday,
                   String course, String semester, ArrayList<Lecture> enrollLectures, ArrayList<Lecture> requestedLectures) {
        this(0, username, password, name, cpf, address, birthday, course, semester, enrollLectures, requestedLectures);
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
