package by.vsu.service.logic;

import by.vsu.config.TestConfig;
import by.vsu.dao.ReviewDao;
import by.vsu.model.Restaurant;
import by.vsu.model.Review;
import by.vsu.service.exception.NotAllowedChangeException;
import by.vsu.service.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ReviewServiceImplTest implements ApplicationContextAware {
    @Mock
    private ReviewDao reviewDao;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private ApplicationContext context;

    @Autowired
    @Qualifier("reviews")
    private List<Review> reviews;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getReviewsTest() {
        when(reviewDao.findAll(anyInt(), anyInt())).thenReturn(reviews);

        List<Review> actual = reviewService.getReviews(reviews.size(), 0);
        assertEquals(reviews, actual);
    }

    @Test
    public void getReviewsByRestaurantIdTest() {
        Integer id = 1;
        List<Review> expected = reviews.stream()
                .filter(review -> review.getRestaurant().getId() == id)
                .collect(Collectors.toList());
        when(reviewDao.findByRestaurantId(anyInt(), anyInt(), anyInt()))
                .thenReturn(expected);

        List<Review> actual = reviewService.getReviewsByRestaurantId(id, reviews.size(), 0);
        assertEquals(expected, actual);
    }

    @Test
    public void getReviewsByRestaurantIdPageSizePageNumTest() {
        Integer restaurantId = 1;
        int pageSize = 1;
        int pageNum = 1;
        List<Review> reviewsByRestaurantId = reviews.stream()
                .filter(review -> review.getRestaurant().getId() == restaurantId)
                .collect(Collectors.toList());
        List<Review> expected = reviewsByRestaurantId.subList(pageSize * pageNum, pageSize * (pageNum + 1));

        when(reviewDao.findByRestaurantId(anyInt(), anyInt(), anyInt()))
                .thenReturn(expected);

        List<Review> actual = reviewService.getReviewsByRestaurantId(restaurantId, pageSize, pageNum);
        assertEquals(expected, actual);
    }

    @Test
    public void getReviewsByUserId() {
        Integer userId = 1;
        List<Review> expected = reviews.stream()
                .filter(review -> review.getUser().getId() == userId)
                .collect(Collectors.toList());
        when(reviewDao.findByUserId(anyInt(), anyInt(), anyInt()))
                .thenReturn(expected);

        List<Review> actual = reviewService.getReviewsByUserId(userId, reviews.size(), 0);
        assertEquals(expected, actual);
    }

    @Test
    public void saveNewObjectTest() {
        Integer id = 1;
        Review review = getReview();
        doAnswer(i -> {
            Review r = i.getArgument(0);
            r.setId(id);
            return null;
        }).when(reviewDao).create(any(Review.class));

        Review actual = reviewService.save(review);
        review.setId(id);
        assertEquals(review, actual);
    }

    @Test
    public void saveExistedObjectTest() {
        Integer id = 1;
        Review review = getReview();
        review.setId(id);
        when(reviewDao.findById(anyInt())).thenReturn(review);

        Review actual = reviewService.save(review);
        assertEquals(review, actual);
    }

    @Test(expected = NotFoundException.class)
    public void saveNotExistedObjectTest() {
        when(reviewDao.findById(anyInt())).thenReturn(null);
        reviewService.save(new Review(1));
    }

    @Test(expected = NotAllowedChangeException.class)
    public void saveExistedObjectNotAllowedChangeTest() {
        Review newReview = getReview();
        newReview.setId(1);
        Review oldReview = getReview();
        oldReview.setRestaurant(new Restaurant(111));

        when(reviewDao.findById(anyInt())).thenReturn(oldReview);
        reviewService.save(newReview);
    }

    @Test
    public void deleteOneTest() {
        Integer id = 1;
        when(reviewDao.delete(anyInt())).thenReturn(1);
        boolean actual = reviewService.delete(id);
        assertTrue(actual);
    }

    @Test
    public void deleteZeroTest() {
        Integer id = 1;
        when(reviewDao.delete(anyInt())).thenReturn(0);
        boolean actual = reviewService.delete(id);
        assertFalse(actual);
    }

    private Review getReview() {
        return context.getBean(Review.class);
    }
}