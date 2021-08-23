package br.ufrrj.samu.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Student extends User {

    private String semester;

    private List<Lecture> enrollLectures;
    private List<Lecture> requestedLectures;
    private List<Subject> concludedSubjects;

    public Student() {
        super();
    }

    public Student(String username, String password, String name, String cpf, String address, String birthday,
                   Course course, String semester, List<Lecture> enrollLectures, List<Lecture> requestedLectures, List<Subject> concludedSubjects) {
        super(username, password, name, cpf, address, birthday, course);
        this.semester = semester;
        this.concludedSubjects = concludedSubjects;

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

    public String getSemester() {
        return semester;
    }

    public List<Lecture> getEnrollLectures() {
        return enrollLectures;
    }

    public List<Lecture> getRequestedLectures() {
        return requestedLectures;
    }

    public Optional<String> getEnrollLecturesIds() {
        return enrollLectures.stream().map(Lecture::getCode).reduce((s1, s2) -> s1 + "," + s2);
    }

    public Optional<String> getRequestedLecturesIds() {
        return requestedLectures.stream().map(Lecture::getCode).reduce((s1, s2) -> s1 + "," + s2);
    }

    public void addEnrollLectures(Lecture lecture) { this.enrollLectures.add(lecture); }

    public void addRequestedLectures(Lecture lecture) { this.requestedLectures.add(lecture); }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setEnrollLectures(List<Lecture> enrollLectures) {
        this.enrollLectures = enrollLectures;
    }

    public void setRequestedLectures(List<Lecture> requestedLectures) {
        this.requestedLectures = requestedLectures;
    }

    public List<Subject> getConcludedSubjects() {
        return concludedSubjects;
    }

    public void setConcludedSubjects(List<Subject> concludedSubjects) {
        this.concludedSubjects = concludedSubjects;
    }

    @Override
    public String toString() {
        return "Student{" +
                "user='" + super.toString() + '\'' +
                ", semester='" + semester + '\'' +
                ", enrollLectures=" + enrollLectures +
                ", requestedLectures=" + requestedLectures +
                ", concludedSubjects=" + concludedSubjects +
                '}';
    }

}
