package com.gmail.marozalena.onlinemarket.service.model;

import java.util.Date;

public class ReviewDTO {

    private Long id;
    private UserDTO user;
    private String review;
    private Date date;
    private boolean isShowed;

    public ReviewDTO(UserDTO user, String review,
                     Date date, boolean isShowed) {
        this.user = user;
        this.review = review;
        this.date = date;
        this.isShowed = isShowed;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isShowed() {
        return isShowed;
    }

    public void setShowed(boolean showed) {
        isShowed = showed;
    }
}
