package com.gmail.marozalena.onlinemarket.service.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleDTO {

    private Date date;
    private String topic;
    private String pathToPicture;
    private UserDTO userDTO;
    private String body;
    private List<CommentDTO> commentsDTO = new ArrayList<>();

    public ArticleDTO() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPathToPicture() {
        return pathToPicture;
    }

    public void setPathToPicture(String pathToPicture) {
        this.pathToPicture = pathToPicture;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<CommentDTO> getCommentsDTO() {
        return commentsDTO;
    }

    public void setCommentsDTO(List<CommentDTO> commentsDTO) {
        this.commentsDTO = commentsDTO;
    }
}
