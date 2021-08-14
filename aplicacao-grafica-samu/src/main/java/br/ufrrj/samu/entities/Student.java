package br.ufrrj.samu.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Student extends User {

    private String course;
    private String semester;

    private List<Lecture> enrollLectures;
    private List<Lecture> requestedLectures;

    public Student() {
        super();
    }

    public Student(long id, String course, String semester, List<Lecture> enrollLectures, List<Lecture> requestedLectures) {
        this(course, semester, enrollLectures, requestedLectures);
        super.setId(id);
    }

    public Student(String course, String semester, List<Lecture> enrollLectures, List<Lecture> requestedLectures) {
        super();
        this.course = course;
        this.semester = semester;
        this.enrollLectures = enrollLectures;
        this.requestedLectures = requestedLectures;
    }

    public Student(long id, String username, String password, String name, String cpf, String address, String birthday,
                   String course, String semester, List<Lecture> enrollLectures, List<Lecture> requestedLectures) {
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
                   String course, String semester, List<Lecture> enrollLectures, List<Lecture> requestedLectures) {
        this(0, username, password, name, cpf, address, birthday, course, semester, enrollLectures, requestedLectures);
    }
    public String getCourse() {
        return course;
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

    public void setCourse(String course) {
        this.course = course;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setEnrollLectures(List<Lecture> enrollLectures) {
        this.enrollLectures = enrollLectures;
    }

    public void setRequestedLectures(List<Lecture> requestedLectures) {
        this.requestedLectures = requestedLectures;
    }

    @Override
    public String toString() {
        return "Student{" +
                "user='" + super.toString() + '\'' +
                ", course='" + course + '\'' +
                ", semester='" + semester + '\'' +
                ", enrollLectures=" + enrollLectures +
                ", requestedLectures=" + requestedLectures +
                '}';
    }

}
