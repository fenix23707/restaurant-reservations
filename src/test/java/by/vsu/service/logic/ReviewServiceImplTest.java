package by.vsu.service.logic;

import by.vsu.config.TestConfig;
import by.vsu.dao.ReviewDao;
import by.vsu.model.Restaurant;
import by.vsu.model.Review;
import by.vsu.model.User;
import by.vsu.service.exception.NotAllowedChangeException;
import by.vsu.service.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Date;
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

    private List<Review> reviews;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public ReviewServiceImplTest() {
        reviews = Arrays.asList(
                new Review(1, 4, "text 1", new Date(), new User(1), new Restaurant(4)),
                new Review(2, 10, "text 2", new Date(), new User(1), new Restaurant(3)),
                new Review(3, 8, "text 2", new Date(), new User(1), new Restaurant(2)),
                new Review(4, 7, "text 2", new Date(), new User(1), new Restaurant(1)),
                new Review(5, 7, "text 2", new Date(), new User(4), new Restaurant(1)),
                new Review(6, 7, "text 2", new Date(), new User(4), new Restaurant(1))
        );
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
        Integer id = 1;
        int pageSize = 1;
        int pageNum = 1;
        List<Review> reviewsByRestaurantId = reviews.stream()
                .filter(review -> review.getRestaurant().getId() == id)
                .collect(Collectors.toList());
        List<Review> expected = reviewsByRestaurantId.subList(pageSize * pageNum, pageSize * (pageNum + 1));

        when(reviewDao.findByRestaurantId(anyInt(), anyInt(), anyInt()))
                .thenReturn(expected);

        List<Review> actual = reviewService.getReviewsByRestaurantId(id, pageSize, pageNum);
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