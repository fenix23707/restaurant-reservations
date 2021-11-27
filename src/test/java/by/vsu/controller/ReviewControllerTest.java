package by.vsu.controller;

import by.vsu.config.TestConfig;
import by.vsu.model.Review;
import by.vsu.service.ReviewService;
import by.vsu.service.exception.NotAllowedChangeException;
import by.vsu.service.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReviewController.class)
@Import(TestConfig.class)
public class ReviewControllerTest implements ApplicationContextAware {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Autowired
    @Qualifier("reviews")
    private List<Review> reviews;

    @Autowired
    private ObjectMapper mapper;

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void allTest() throws Exception {
        when(reviewService.getReviews(anyInt(), anyInt())).thenReturn(reviews);

        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(reviews)));
    }

    @Test
    public void allByRestaurantIdTest() throws Exception {
        Integer restaurantId = 1;

        List<Review> reviewsByRestaurantId = reviews.stream()
                .filter(review -> review.getRestaurant().getId() == restaurantId)
                .collect(Collectors.toList());

        when(reviewService.getReviewsByRestaurantId(anyInt(), anyInt(), anyInt()))
                .thenReturn(reviewsByRestaurantId);


        mockMvc.perform(get("/reviews/restaurant/" + restaurantId))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(reviewsByRestaurantId)));
    }

    @Test
    public void allByUserIdTest() throws Exception {
        Integer userId = 1;

        List<Review> reviewsByUserId = reviews.stream()
                .filter(review -> review.getUser().getId() == userId)
                .collect(Collectors.toList());

        when(reviewService.getReviewsByRestaurantId(anyInt(), anyInt(), anyInt()))
                .thenReturn(reviewsByUserId);


        mockMvc.perform(get("/reviews/restaurant/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(reviewsByUserId)));
    }

    @Test
    public void newReviewTest() throws Exception{
        Integer id = 1;
        Review response = getReview();
        response.setId(id);
        Review review = getReview();
        review.setId(null);
        doAnswer(i -> {
            Review r = i.getArgument(0);
            r.setId(id);
            return  r;
        }).when(reviewService).save(any());


        mockMvc.perform(post("/reviews")
                .content(mapper.writeValueAsString(review))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @Test
    public void newReviewConflictTest() throws Exception{
        Review review = getReview();
        review.setId(1);
        when(reviewService.save(any())).thenReturn(review);

        mockMvc.perform(post("/reviews")
                .content(mapper.writeValueAsString(review))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }


    @Test
    public void updateReviewTest() throws Exception{
        Review review = getReview();
        review.setId(1);
        when(reviewService.save(any())).thenReturn(review);

        mockMvc.perform(put("/reviews")
                .content(mapper.writeValueAsString(review))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateBadRequestTest() throws Exception{
        Review review = getReview();
        review.setId(null);

        mockMvc.perform(put("/reviews")
                .content(mapper.writeValueAsString(review))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateNotAllowedChangeTest() throws Exception{
        Review review = getReview();
        review.setId(1);

        when(reviewService.save(any())).thenThrow(new NotAllowedChangeException());

        mockMvc.perform(put("/reviews")
                .content(mapper.writeValueAsString(review))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateNotFoundTest() throws Exception{
        Review review = getReview();
        review.setId(1);

        when(reviewService.save(any())).thenThrow(new NotFoundException());

        mockMvc.perform(put("/reviews")
                .content(mapper.writeValueAsString(review))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void deleteNoContentTest() throws Exception {
        when(reviewService.delete(anyInt())).thenReturn(true);

        mockMvc.perform(delete("/reviews/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteNotFoundTest() throws Exception {
        when(reviewService.delete(anyInt())).thenReturn(false);

        mockMvc.perform(delete("/reviews/1"))
                .andExpect(status().isNotFound());
    }

    private Review getReview() {
        return context.getBean("review", Review.class);
    }
}