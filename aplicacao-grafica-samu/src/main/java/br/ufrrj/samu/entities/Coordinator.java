package br.ufrrj.samu.entities;

import java.util.List;

public class Coordinator extends Teacher {

    public Coordinator(String username, String password, String name, String cpf, String address, String birthday, List<Lecture> lectures, Course course) {
        super(username, password, name, cpf, address, birthday, lectures, course);
    }

    @Override
    public String toString() {
        return "Coordinator{" + super.toString() + "}";
    }

}
