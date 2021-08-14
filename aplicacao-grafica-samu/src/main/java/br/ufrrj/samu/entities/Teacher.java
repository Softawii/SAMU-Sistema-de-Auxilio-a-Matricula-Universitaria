package br.ufrrj.samu.entities;

import java.util.List;
import java.util.Optional;

public class Teacher extends User {

    public Teacher() {
        super();
    }

    private List<Lecture> lectures;

    private String course;

    public Teacher(List<Lecture> lectures, String course) {
        this.lectures = lectures;
        this.course = course;
    }

    public Teacher(String username, String password, String name, String cpf, String address, String birthday, List<Lecture> lectures, String course) {
        super(username, password, name, cpf, address, birthday);
        this.lectures = lectures;
        this.course = course;
    }

    public Teacher(long id, String username, String password, String name, String cpf, String address, String birthday, List<Lecture> lectures, String course) {
        super(id, username, password, name, cpf, address, birthday);
        this.lectures = lectures;
        this.course = course;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public Optional<String> getLecturesIds() {
        return lectures.stream().map(Lecture::getCode).reduce((s1, s2) -> s1 + "," + s2);
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public void addLecture(Lecture lecture) {
        this.lectures.add(lecture);
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "user=" + super.toString() +
                ", lectures='" + lectures + '\'' +
                ", course='" + course + '\'' +
                '}';
    }

}
