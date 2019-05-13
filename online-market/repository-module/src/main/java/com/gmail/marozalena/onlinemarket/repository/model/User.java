package com.gmail.marozalena.onlinemarket.repository.model;

public class User {

    private Long id;
    private String surname;
    private String name;
    private String patronymic;
    private String email;
    private String password;
    private Role role;
    private boolean isDeleted;

    public User(String surname, String name, String patronymic, String email,
                String password, Role role, boolean isDeleted) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isDeleted = isDeleted;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
