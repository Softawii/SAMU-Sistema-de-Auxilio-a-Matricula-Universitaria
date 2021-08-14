package br.ufrrj.samu.entities;

import java.util.List;

public class Lecture {
    private String classPlan;
    private String classRoom;
    private String schedule;


    private final String code;
    private final Subject subject;

    private Long teacher;

    private List<String> students;

    public Lecture(String classPlan, String classRoom, String schedule, String code, Subject subject, Long teacher, List<String> students) {
        this.classPlan = classPlan;
        this.classRoom = classRoom;
        this.schedule = schedule;
        this.code = code;
        this.subject = subject;
        this.teacher = teacher;
        this.students = students;
    }

    public static String parseListOfLecture(List<Lecture> lecture) {
        return lecture.stream()
                .map(Lecture::getCode)
                .reduce((s1, s2) -> s1 + "," + s2)
                .orElse("");
    }

    public String getClassPlan() {
        return classPlan;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getCode() {
        return code;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setClassPlan(String classPlan) {
        this.classPlan = classPlan;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public Long getTeacher() {
        return teacher;
    }

    public String getStudentsIds() {
        return students.stream()
                .reduce((s1, s2) -> s1 + "," + s2)
                .orElse("");
    }

    public List<String> getStudents() {
        return students;
    }

    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Lecture{" +
                "classPlan='" + classPlan + '\'' +
                ", classRoom='" + classRoom + '\'' +
                ", schedule='" + schedule + '\'' +
                ", code='" + code + '\'' +
                ", subject=" + subject +
                ", teacher=" + teacher +
                ", students=" + students +
                '}';
    }
}
