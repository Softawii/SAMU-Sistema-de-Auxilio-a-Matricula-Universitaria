package br.ufrrj.samu.entities;

public class Lecture {
    private String classPlan;
    private String classRoom;
    private String schedule;

    private final String code;
    private final Subject subject;

    public Lecture(String classPlan, String classRoom, String schedule, String code, Subject subject) {
        this.classPlan = classPlan;
        this.classRoom = classRoom;
        this.schedule = schedule;
        this.code = code;
        this.subject = subject;
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

    @Override
    public String toString() {
        return "Lecture{" +
                "classPlan='" + classPlan + '\'' +
                ", classRoom='" + classRoom + '\'' +
                ", schedule='" + schedule + '\'' +
                ", code='" + code + '\'' +
                ", subject=" + subject +
                '}';
    }
}
