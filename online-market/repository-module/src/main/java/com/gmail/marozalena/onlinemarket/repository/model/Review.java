package com.gmail.marozalena.onlinemarket.repository.model;

import java.util.Date;

public class Review {

    private Long id;
    private User user;
    private String review;
    private Date date;
    private boolean isShowed;

    public Review(User user, String review, Date date, boolean isShowed) {
        this.user = user;
        this.review = review;
        this.date = date;
        this.isShowed = isShowed;
    }

    public Review() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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
