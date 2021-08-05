package br.ufrrj.samu.entities;

import java.util.ArrayList;
import java.util.List;

public class Lecture {
    private String classPlan;
    private String classRoom;
    private String schedule;


    private final String code;
    private final Subject subject;

    private Teacher teacher;

    private List<Student> students;

    public Lecture(String classPlan, String classRoom, String schedule, String code, Subject subject, Teacher teacher, List<Student> students) {
        this.classPlan = classPlan;
        this.classRoom = classRoom;
        this.schedule = schedule;
        this.code = code;
        this.subject = subject;
        this.teacher = teacher;
        this.students = students;
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

    public Teacher getTeacher() {
        return teacher;
    }

    public String getStudentsIds() {

        StringBuilder stringBuilder = new StringBuilder();

        for(Student student : this.students) {
            stringBuilder.append(student.getId() + ",");
        }

        if(!stringBuilder.isEmpty()) {
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        }

        return stringBuilder.toString();

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
