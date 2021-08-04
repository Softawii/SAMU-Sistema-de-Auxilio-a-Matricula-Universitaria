package br.ufrrj.samu.entities;

import java.util.Objects;

public class User {

    private long id;
    private String username;
    private String password;
    private String name;
    private String cpf;
    private String address;
    private String birthday;

    public User() {
    }

    public User(String username, String password) {
        this(0, username, password);
    }

    public User(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String name, String cpf, String address, String birthday) {
        this(0, username, password, name, cpf, address, birthday);
    }

    public User(long id, String username, String password, String name, String cpf, String address, String birthday) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.cpf = cpf;
        this.address = address;
        this.birthday = birthday;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user1 = (User) o;
        return username.equals(user1.username) && password.equals(user1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
