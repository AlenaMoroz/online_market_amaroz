package com.gmail.marozalena.onlinemarket.service.model;

import java.util.List;

public class ListOfReviewsDTO {

    private List<ReviewDTO> listOfReviews;

    public ListOfReviewsDTO(List<ReviewDTO> listOfReviews) {
        this.listOfReviews = listOfReviews;
    }

    public ListOfReviewsDTO() {
    }

    public List<ReviewDTO> getListOfReviews() {
        return listOfReviews;
    }

    public void setListOfReviews(List<ReviewDTO> listOfReviews) {
        this.listOfReviews = listOfReviews;
    }

}
