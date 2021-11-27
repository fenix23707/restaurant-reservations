package by.vsu.controller;

import by.vsu.model.Review;
import by.vsu.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private ReviewService reviewService;

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> all(@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                            @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
        return reviewService.getReviews(pageSize, pageNum);
    }

    @GetMapping("/restaurant/{id}")
    public List<Review> allByRestaurantId(@PathVariable("id") Integer restaurantId,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                          @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
        return reviewService.getReviewsByRestaurantId(restaurantId, pageSize, pageNum);
    }

    @GetMapping("user/{id}")
    public List<Review> allByUserId(@PathVariable("id") Integer userId,
                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                    @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
        return reviewService.getReviewsByUserId(userId, pageSize, pageNum);
    }

    @PostMapping()
    public ResponseEntity<Review> newReview(@RequestBody Review review) {
        ResponseEntity<Review> responseEntity = null;
        if (review.getId() == null) {
            Review created = reviewService.save(review);
            responseEntity = new ResponseEntity(created, HttpStatus.CREATED);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @PutMapping
    public ResponseEntity<Review> updateReview(@RequestBody Review review) {
        ResponseEntity<Review> responseEntity;
        if (review.getId() != null) {
            Review updated = reviewService.save(review);
            responseEntity = new ResponseEntity<Review>(updated, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Review> delete(@PathVariable("id") Integer id) {
        ResponseEntity<Review> responseEntity = null;
        if (reviewService.delete(id)) {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

}
