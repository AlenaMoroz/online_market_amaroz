package com.gmail.marozalena.onlinemarket.repository.model;

import java.util.Date;

public class Review {

    private Long id;
    private User user;
    private String review;
    private Date date;
    private boolean showed;

    public Review(User user, String review, Date date, boolean showed) {
        this.user = user;
        this.review = review;
        this.date = date;
        this.showed = showed;
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

    public boolean showed() {
        return showed;
    }

    public void setShowed(boolean showed) {
        this.showed = showed;
    }
}
