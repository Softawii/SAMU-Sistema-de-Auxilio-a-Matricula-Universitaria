package br.ufrrj.samu.entities;

public class Teacher extends User {

    public Teacher() {
        super();
    }

    public Teacher(String username, String password) {
        super(username, password);
    }

    public Teacher(long id, String username, String password) {
        super(id, username, password);
    }

    public Teacher(String username, String password, String name, String cpf, String address, String birthday) {
        super(username, password, name, cpf, address, birthday);
    }

    public Teacher(long id, String username, String password, String name, String cpf, String address, String birthday) {
        super(id, username, password, name, cpf, address, birthday);
    }
}
