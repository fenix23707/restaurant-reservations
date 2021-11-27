package by.vsu.service;

import by.vsu.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviews(int pageSize, int pageNum);

    List<Review> getReviewsByRestaurantId(Integer restaurantId, int pageSize, int pageNum);

    List<Review> getReviewsByUserId(Integer userId, int pageSize, int pageNum);

    Review save(Review review);

    boolean delete(Integer id);
}
