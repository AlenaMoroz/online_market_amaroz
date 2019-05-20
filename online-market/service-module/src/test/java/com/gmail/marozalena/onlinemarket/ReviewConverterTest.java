package com.gmail.marozalena.onlinemarket;

import com.gmail.marozalena.onlinemarket.repository.model.Review;
import com.gmail.marozalena.onlinemarket.repository.model.Role;
import com.gmail.marozalena.onlinemarket.repository.model.User;
import com.gmail.marozalena.onlinemarket.service.converter.ReviewConverter;
import com.gmail.marozalena.onlinemarket.service.converter.impl.ProfileConverterImpl;
import com.gmail.marozalena.onlinemarket.service.converter.impl.ReviewConverterImpl;
import com.gmail.marozalena.onlinemarket.service.converter.impl.RoleConverterImpl;
import com.gmail.marozalena.onlinemarket.service.converter.impl.UserConverterImpl;
import com.gmail.marozalena.onlinemarket.service.model.ReviewDTO;
import com.gmail.marozalena.onlinemarket.service.model.RoleDTO;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

public class ReviewConverterTest {

    private ReviewConverter reviewConverter;

    @Before
    public void init() {
        reviewConverter = new ReviewConverterImpl(new UserConverterImpl(
                new RoleConverterImpl(), new ProfileConverterImpl()));
    }

    @Test
    public void shouldConvertReviewDTOWithIdToReview() {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(2L);
        Review review = reviewConverter.fromReviewDTO(reviewDTO);
        Assert.assertEquals(reviewDTO.getId(), review.getId());
    }

    @Test
    public void shouldConvertReviewDTOWithUserToReview() {
        ReviewDTO reviewDTO = new ReviewDTO();
        UserDTO userDTO = new UserDTO();
        RoleDTO roleDTO = new RoleDTO();
        userDTO.setEmail("Email");
        userDTO.setPassword("Password");
        userDTO.setRole(roleDTO);
        reviewDTO.setUser(userDTO);
        Review review = reviewConverter.fromReviewDTO(reviewDTO);
        Assert.assertEquals(reviewDTO.getUser().getEmail(), review.getUser().getEmail());
    }

    @Test
    public void shouldConvertReviewDTOWithReviewToReview() {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setReview("TestReview");
        Review review = reviewConverter.fromReviewDTO(reviewDTO);
        Assert.assertEquals(reviewDTO.getReview(), review.getReview());
    }

    @Test
    public void shouldConvertReviewDTOWithDateToReview() {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setDate(new Timestamp(new Date().getTime()));
        Review review = reviewConverter.fromReviewDTO(reviewDTO);
        Assert.assertEquals(reviewDTO.getDate(), review.getDate());
    }

    @Test
    public void shouldConvertReviewDTOWithShowedToReview() {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setShowed(true);
        Review review = reviewConverter.fromReviewDTO(reviewDTO);
        Assert.assertEquals(reviewDTO.isShowed(), review.isShowed());
    }

    @Test
    public void shouldConvertReviewWithIdToReviewDTO() {
        Review review = new Review();
        review.setId(2L);
        ReviewDTO reviewDTO = reviewConverter.toReviewDTO(review);
        Assert.assertEquals(review.getId(), reviewDTO.getId());
    }

    @Test
    public void shouldConvertReviewWithUserToReviewDTO() {
        Review review = new Review();
        User user = new User();
        Role role = new Role();
        user.setEmail("Email");
        user.setPassword("Password");
        user.setRole(role);
        review.setUser(user);
        ReviewDTO reviewDTO = reviewConverter.toReviewDTO(review);
        Assert.assertEquals(review.getUser().getEmail(), reviewDTO.getUser().getEmail());
    }

    @Test
    public void shouldConvertReviewWithReviewToReviewDTO() {
        Review review = new Review();
        review.setReview("TestReview");
        ReviewDTO reviewDTO = reviewConverter.toReviewDTO(review);
        Assert.assertEquals(review.getReview(), reviewDTO.getReview());
    }

    @Test
    public void shouldConvertReviewWithDateToReviewDTO() {
        Review review = new Review();
        review.setDate(new Timestamp(new Date().getTime()));
        ReviewDTO reviewDTO = reviewConverter.toReviewDTO(review);
        Assert.assertEquals(review.getDate(), reviewDTO.getDate());
    }

    @Test
    public void shouldConvertReviewWithShowedToReviewDTO() {
        Review review = new Review();
        review.setShowed(false);
        ReviewDTO reviewDTO = reviewConverter.toReviewDTO(review);
        Assert.assertEquals(review.isShowed(), reviewDTO.isShowed());
    }
}
