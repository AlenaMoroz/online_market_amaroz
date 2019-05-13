package com.gmail.marozalena.onlinemarket.service.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDTO {

    private Long id;
    @NotNull
    @Size(max = 40)
    private String surname;
    @NotNull
    @Size(max = 20)
    private String name;
    @NotNull
    @Size(max = 40)
    private String patronymic;
    @NotNull
    @Size(max = 50)
    @Email
    private String email;
    private String password;
    private RoleDTO role;
    private boolean isDeleted;

    public UserDTO(String surname, String name, String patronymic, String email,
                   String password, RoleDTO role, boolean isDeleted) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isDeleted = isDeleted;
    }

    public UserDTO() {
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

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
