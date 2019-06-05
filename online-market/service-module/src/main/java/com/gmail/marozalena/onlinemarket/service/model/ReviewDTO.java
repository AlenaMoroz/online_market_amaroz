package com.gmail.marozalena.onlinemarket.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ReviewDTO {

    private Long id;
    private UserDTO user;
    @NotNull
    @Size(max = 200)
    private String review;
    private String date;
    private boolean showed;

    public ReviewDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isShowed() {
        return showed;
    }

    public void setShowed(boolean showed) {
        this.showed = showed;
    }
}
