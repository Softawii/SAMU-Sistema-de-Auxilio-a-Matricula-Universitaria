package br.ufrrj.samu.entities;

import br.ufrrj.samu.services.SubjectService;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private String name;
    private String address;

    private ArrayList<Subject> subjects;

    public Student() {
        super();
    }

    public Student(String username, String password, String name, String address, List<Subject> subjects) {
        super(username, password);
        this.name = name;
        this.address = address;

        try {
            this.subjects = new ArrayList<>(subjects);
        } catch(NullPointerException e) {
            this.subjects = new ArrayList<>();
        }
    }

    public Student(long id, String username, String password, String name, String address, List<Subject> subjects) {
        super(id, username, password);
        this.name = name;
        this.address = address;

        try {
            this.subjects = new ArrayList<>(subjects);
        } catch(NullPointerException e) {
            this.subjects = new ArrayList<>();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void addSubject(Subject subject) {
        this.subjects.add(subject);
    }

    public void removeSubject(Subject subject) {
        this.subjects.remove(subject);
    }


    public String getSubjectsCodes() {
        // Maybe we can get a better name to this
        StringBuilder stringBuilder = new StringBuilder();

        for(Subject subject : this.subjects) {
            stringBuilder.append(subject.getCode() + ",");
        }

        if(!stringBuilder.isEmpty()) {
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        }

        return stringBuilder.toString();
    }
}
