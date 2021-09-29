package br.ufrrj.samu.entities;

import java.util.List;
import java.util.Optional;

public class Teacher extends User {

    public Teacher() {
        super();
    }

    private List<Lecture> lectures;

    public Teacher(String username, String password, String name, String cpf, String address, String birthday, List<Lecture> lectures, Course course) {
        super(username, password, name, cpf, address, birthday, course);
        this.lectures = lectures;
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

    @Override
    public String toString() {
        return "Teacher{" +
                ", lectures='" + lectures.stream().map(Lecture::getCode) + '\'' +
                '}';
    }

}
