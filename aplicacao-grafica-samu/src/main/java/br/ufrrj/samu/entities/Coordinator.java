package br.ufrrj.samu.entities;

public class Coordinator extends Teacher {

    public Coordinator() {
    }

    public Coordinator(String username, String password) {
        super(username, password);
    }

    public Coordinator(long id, String username, String password) {
        super(id, username, password);
    }

    public Coordinator(String username, String password, String name, String cpf, String address, String birthday) {
        super(username, password, name, cpf, address, birthday);
    }

    public Coordinator(long id, String username, String password, String name, String cpf, String address, String birthday) {
        super(id, username, password, name, cpf, address, birthday);
    }
}
