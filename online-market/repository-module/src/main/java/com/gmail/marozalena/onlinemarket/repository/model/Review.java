package com.gmail.marozalena.onlinemarket.repository.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "reviews")
@SQLDelete(sql = "UPDATE reviews SET deleted = '1' WHERE id = ?")
@Where(clause = "deleted = '0'")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user = new User();
    @Column(name = "review")
    private String review;
    @Column(name = "date")
    private Date date;
    @Column(name = "showed")
    private boolean showed;

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
        return showed;
    }

    public void setShowed(boolean showed) {
        this.showed = showed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review1 = (Review) o;
        return showed == review1.showed &&
                Objects.equals(id, review1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, showed);
    }
}
