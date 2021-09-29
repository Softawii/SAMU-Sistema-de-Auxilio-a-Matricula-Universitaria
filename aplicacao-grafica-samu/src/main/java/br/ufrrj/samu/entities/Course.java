package br.ufrrj.samu.entities;

import java.util.List;

public class Course {

    private String name;
    private List<Teacher> teachers;
    private Coordinator coordinator;
    private List<Curriculum> curriculum;
    private List<Student> students;

    public Course(String name, Curriculum curriculum) {
        this.name = name;
        this.curriculum = List.of(curriculum);
    }

    public String getName() {
        return name;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Coordinator getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    public void setCurriculum(List<Curriculum> curriculum) {
        this.curriculum = curriculum;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Curriculum getCurriculum() {
        return curriculum.get(0);
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", teachers=" + teachers +
                ", coordinator=" + coordinator +
                ", curriculum=" + curriculum +
                '}';
    }
}
