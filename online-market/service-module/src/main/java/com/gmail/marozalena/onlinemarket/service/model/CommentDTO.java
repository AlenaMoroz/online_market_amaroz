package com.gmail.marozalena.onlinemarket.service.model;

import java.util.Date;

public class CommentDTO {

    private UserDTO userDTO;
    private Date date;
    private String comment;

    public CommentDTO() {
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
