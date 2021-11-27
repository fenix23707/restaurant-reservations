package by.vsu.service.logic;

import by.vsu.dao.ReviewDao;
import by.vsu.model.Review;
import by.vsu.service.ReviewService;
import by.vsu.service.exception.NotAllowedChangeException;
import by.vsu.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewDao reviewDao;

    @Autowired
    public void setReviewDao(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Override
    public List<Review> getReviews(int pageSize, int pageNum) {
        return reviewDao.findAll(pageSize, pageNum * pageSize);
    }

    @Override
    public List<Review> getReviewsByRestaurantId(Integer restaurantId, int pageSize, int pageNum) {
        return reviewDao.findByRestaurantId(restaurantId, pageSize, pageNum * pageSize);
    }

    @Override
    public List<Review> getReviewsByUserId(Integer userId, int pageSize, int pageNum) {
        return reviewDao.findByUserId(userId, pageSize, pageNum * pageSize);
    }

    @Override
    public Review save(Review review) {
        if (review.getId() == null) {
            reviewDao.create(review);
        } else {
            Review oldReview = reviewDao.findById(review.getId());
            if (oldReview == null) {
                throw new NotFoundException("Review with id = " + review.getId() + " not found.");
            }
            if (changeFieldsThatCantBeChange(oldReview, review)) {
                throw new NotAllowedChangeException();
            }
            reviewDao.update(review);
        }
        return review;
    }

    @Override
    public boolean delete(Integer id) {
        return reviewDao.delete(id) != 0;
    }

    private boolean changeFieldsThatCantBeChange(Review oldReview, Review newReview) {
        return !oldReview.getUser().equals(newReview.getUser()) ||
                !oldReview.getRestaurant().equals(newReview.getRestaurant());
    }
}
