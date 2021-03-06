package by.vsu.dao;

import by.vsu.config.AppConfig;
import by.vsu.model.Restaurant;
import by.vsu.model.Review;
import by.vsu.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ReviewDaoTest {
    private final static int REVIEWS_SIZE = 5;

    private ReviewDao reviewDao;

    @Autowired
    public void setReviewDao(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Test
    public void findAllSizeTest() {
        List<Review> reviews = reviewDao.findAll(REVIEWS_SIZE, 0);
        assertEquals(REVIEWS_SIZE, reviews.size());
    }

    @Test
    public void findAllFieldsNotNullTest() {
        List<Review> reviews = reviewDao.findAll(REVIEWS_SIZE, 0);
        boolean result = reviews.stream()
                .allMatch(r -> fieldsNotNull(r));
        assertTrue(result);
    }

    @Test
    public void findByIdTest() {
        Integer id = 1;
        Review review = reviewDao.findById(id);

        System.out.println(review);
        assertTrue(fieldsNotNull(review));
        assertEquals("id doesn't match", id, review.getId());
    }

    @Test
    public void findByRestaurantIdTest() {
        Integer restaurantId = 1;
        List<Review> reviews = reviewDao.findByRestaurantId(restaurantId, REVIEWS_SIZE, 0);

        assertTrue(reviews.stream().allMatch(review -> review.getRestaurant().getId() == restaurantId));

        assertTrue(reviews.stream().allMatch(review -> fieldsNotNull(review)));
    }

    @Test
    public void findByUserIdTest() {
        Integer userId = 6;
        List<Review> reviews = reviewDao.findByUserId(userId, REVIEWS_SIZE, 0);

        assertTrue(reviews.stream().allMatch(review -> fieldsNotNull(review)));
        assertTrue(reviews.stream().allMatch(review -> review.getUser().getId() == userId));
    }

    @Test
    @Transactional
    public void createChangeStartSizeTest() {
        assertEquals("start size doesn't match",
                REVIEWS_SIZE,
                reviewDao.findAll(REVIEWS_SIZE, 0).size());

        Review review = getReview();
        reviewDao.create(review);
        int expectedSize = REVIEWS_SIZE + 1;
        assertEquals("size after create new review doesn't match",
                expectedSize,
                reviewDao.findAll(expectedSize, 0).size());
    }

    @Test
    @Transactional
    public void createAutogeneratedIdTest() {
        Review review = getReview();
        review.setId(null);

        reviewDao.create(review);

        assertNotNull(review.getId());
    }

    @Test
    @Transactional
    public void updateTest() {
        Integer id = 1;
        Review newReview = getReview();
        newReview.setId(id);
        Review review = reviewDao.findById(id);

        assertNotEquals(newReview, review);// if this objects equal then this test useless

        reviewDao.update(newReview);
        Review updated = reviewDao.findById(id);
        assertEquals(newReview, updated);
    }

    @Test
    @Transactional
    public void deleteChangeSizeTest() {
        assertEquals("start size doesn't match",
                REVIEWS_SIZE,
                reviewDao.findAll(REVIEWS_SIZE, 0).size());

        Integer id = 1;
        reviewDao.delete(id);
        int expectedSize = REVIEWS_SIZE - 1;
        assertEquals("size after delete review doesn't match",
                expectedSize,
                reviewDao.findAll(expectedSize, 0).size());
    }

    private boolean fieldsNotNull(Review review) {
        return review.getId() != null &&
                review.getDate() != null &&
                review.getText() != null &&
                review.getRestaurant().getId() != null &&
                review.getUser().getId() != null;
    }

    private Review getReview() {
        User user = new User();
        user.setId(1);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);

        return new Review(10, "test", new Date(), user, restaurant);
    }
}