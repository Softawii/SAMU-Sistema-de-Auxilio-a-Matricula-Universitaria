package br.ufrrj.samu.entities;

import br.ufrrj.samu.exceptions.WrongRequestedUserTypeException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Period {

    private String name;

    private List<Lecture> lectureList;

    public Period() {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int monthValue = now.getMonthValue(); // 1-12
        int semester = monthValue <= 6 ? 1 : 2;
        this.name = String.format("%d.%d", year, semester);
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

    public List<Lecture> getAvailableLectures(User user) throws WrongRequestedUserTypeException {
        if (userType(user) != User.Type.STUDENT) {
            throw new WrongRequestedUserTypeException("Current user is not a Student");
        }

        Student student = (Student) user;
        List<Subject> concludedSubjects = student.getConcludedSubjects();
        List<String> concludedSubjectsCodes = concludedSubjects.stream().map(Subject::getCode).collect(Collectors.toList());
        List<Lecture> availableLectures = lectureList.stream()
                .filter(lecture -> {
                    ArrayList<String> prerequisites = lecture.getSubject().getPrerequisites();
                    for (String prerequisite : prerequisites) {
                        if (!concludedSubjectsCodes.contains(prerequisite)) {
                            return false;
                        }
                    }
                    return true;
                }).collect(Collectors.toList());

//                        lecture.getSubject().getPrerequisites().stream()
//                                .map(preReq ->
//                                        concludedSubjects.stream()
//                                                .map(Subject::getCode)
//                                                .anyMatch(s -> preReq.equals(s))).reduce()
        return availableLectures;
    }

}
