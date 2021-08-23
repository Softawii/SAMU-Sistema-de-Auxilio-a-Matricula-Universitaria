package br.ufrrj.samu.entities;

import br.ufrrj.samu.exceptions.WrongRequestedUserTypeException;
import br.ufrrj.samu.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Semester {

    private String name;

    private List<Lecture> lectureList;

    public Semester() {
        this.name = Util.getCurrentSemester();
        this.lectureList = new ArrayList<>();
    }

    public void addLecture(Lecture lecture) {
        this.lectureList.add(lecture);
    }

    private User.Type userType(User user) {
        if(user instanceof Student)
            return User.Type.STUDENT;
        else if(user instanceof Teacher)
            return User.Type.TEACHER;
        return User.Type.OTHER;
    }

    public List<Lecture> getLectureList() {
        return lectureList;
    }

    public List<Lecture> getAvailableLectures(User user) throws WrongRequestedUserTypeException {
        if (userType(user) != User.Type.STUDENT) {
            throw new WrongRequestedUserTypeException("Current user is not a Student");
        }

        Student student = (Student) user;
        System.out.println("[Period Lectures]" + lectureList);

        List<Subject> concludedSubjects = student.getConcludedSubjects();

        List<String> concludedSubjectsCodes = concludedSubjects.stream().map(Subject::getCode).collect(Collectors.toList());

        List<Lecture> availableLectures = lectureList.stream()
                .filter(lecture -> !student.getEnrollLectures().contains(lecture))
                .filter(lecture -> !student.getRequestedLectures().contains(lecture))
                .filter(Lecture.isEnrollablePredicate(concludedSubjectsCodes)).collect(Collectors.toList());
        return availableLectures;
    }

    @Override
    public String toString() {
        return "Period{" +
                "name='" + name + '\'' +
                ", lectureList=" + lectureList +
                '}';
    }

    public enum CurrentStatus {
        ENROLLMENT,
        ONGOING,
        CONCLUDED
    }
}
