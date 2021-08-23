package br.ufrrj.samu.entities;

import java.util.*;
import java.util.function.Predicate;

public class Lecture {
    private String classPlan;
    private String classRoom;
    private String schedule;

    private final String code;
    private final Subject subject;

    private Teacher teacher;

    private List<Student> students;

    private List<Student> preEnrolledStudent;

    private Evaluator evaluator;

    public Lecture(String classPlan, String classRoom, String schedule, String code, Subject subject, Teacher teacher, List<Student> students, List<Student> preEnrolledStudent) {
        this.classPlan = classPlan;
        this.classRoom = classRoom;
        this.schedule = schedule;
        this.code = code;
        this.subject = subject;
        this.teacher = teacher;
        this.students = students;
        this.preEnrolledStudent = preEnrolledStudent;
        this.evaluator = new Evaluator();
    }

    public static String parseListOfLecture(List<Lecture> lecture) {
        return lecture.stream()
                .map(Lecture::getCode)
                .reduce((s1, s2) -> s1 + "," + s2)
                .orElse("");
    }

    public void addPreEnrolledStudent(Student student) {
        preEnrolledStudent.add(student);
    }

    public List<Student> getPreEnrolledStudent() {
        return preEnrolledStudent;
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

    public void evaluate(Student student, int rate) {
        evaluator.evaluate(student, rate);
    }

    public boolean hasAlreadyEvaluated(Student student) {
        return evaluator.hasAlreadyEvaluated(student);
    }

    public double getAverage() {
        return evaluator.calculateAverage();
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public static Predicate<Lecture> isEnrollablePredicate(List<String> concludedSubjectsCodes) {
        return lecture -> {
            ArrayList<String> prerequisites = lecture.getSubject().getPrerequisites();

            if (prerequisites.size() == 0) {
                return true;
            }

            for (String prerequisite : prerequisites) {
                if (!concludedSubjectsCodes.contains(prerequisite)) {
                    return false;
                }
            }
            return true;
        };
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

    class Evaluator {
        private Map<Student, Integer> rates;

        public Evaluator() {
            this.rates = new HashMap<>();
        }

        public void evaluate(Student student, int rate) {
            rates.put(student, rate);
        }

        public double calculateAverage() {
            OptionalDouble average = rates.values().stream().mapToInt(Integer::intValue).average();
            return average.orElse(-1);
        }

        public boolean hasAlreadyEvaluated(Student student) {
            return rates.containsKey(student);
        }
    }
}
