package br.ufrrj.samu.entities;

public class Student extends User {

    private String name;
    private String address;
    private String courses;

    public Student() {
        super();
    }

    public Student(String username, String password, String name, String address, String courses) {
        super(username, password);
        this.name = name;
        this.address = address;
        this.courses = courses;
    }

    public Student(long id, String username, String password, String name, String address, String courses) {
        super(id, username, password);
        this.name = name;
        this.address = address;
        this.courses = courses;
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

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }
}
