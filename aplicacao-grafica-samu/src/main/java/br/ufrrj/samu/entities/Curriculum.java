package br.ufrrj.samu.entities;

import java.util.List;

public class Curriculum {

    private String name;
    private List<Subject> optionalSubjects;
    private List<Subject> coreSubjects;

    public Curriculum() {
        this.name = "2077";
    }

    public Curriculum(List<Subject> optionalSubjects, List<Subject> coreSubjects) {
        this.name = "2077";
        this.optionalSubjects = optionalSubjects;
        this.coreSubjects = coreSubjects;
    }

    public void setOptionalSubjects(List<Subject> optionalSubjects) {
        this.optionalSubjects = optionalSubjects;
    }

    public void setCoreSubjects(List<Subject> coreSubjects) {
        this.coreSubjects = coreSubjects;
    }

    public String getName() {
        return name;
    }

    public Subject getSubject(String code) {
        return null;
    }

    @Override
    public String toString() {
        return "Curriculum{" +
                "name='" + name + '\'' +
                ", optionalSubjects=" + optionalSubjects +
                ", coreSubjects=" + coreSubjects +
                '}';
    }
}
