package by.vsu.config;

import by.vsu.model.Restaurant;
import by.vsu.model.Review;
import by.vsu.model.Scheme;
import by.vsu.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Configuration
@MapperScan("by.vsu.dao")
@ImportResource("classpath:beans.xml")
public class TestConfig {
    @Bean
    @Scope("prototype")
    public Review review() {
        User user = new User();
        user.setId(1);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);

        return new Review(10, "test", new Date(), user, restaurant);
    }

    @Bean
    public List<Review> reviews() {
        return Arrays.asList(
                new Review(1, 4, "text 1", new Date(), new User(1), new Restaurant(4)),
                new Review(2, 10, "text 2", new Date(), new User(1), new Restaurant(3)),
                new Review(3, 8, "text 2", new Date(), new User(1), new Restaurant(2)),
                new Review(4, 7, "text 2", new Date(), new User(1), new Restaurant(1)),
                new Review(5, 7, "text 2", new Date(), new User(4), new Restaurant(1)),
                new Review(6, 7, "text 2", new Date(), new User(4), new Restaurant(1))
        );
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        mapper.setDateFormat(dateFormat);
        return mapper;
    }

    @Bean
    @Scope("prototype")
    public Restaurant restaurant() {
        User user = new User();
        user.setId(1);
        return new Restaurant("name", "avatar", "address", user);
    }

    @Bean
    public List<Restaurant> restaurants() {
        return Arrays.asList(
                new Restaurant(1, "name 1", "avatar 1", "address 1", new User(1)),
                new Restaurant(2, "asgasfgasd", "avatar 1", "address 1", new User(1)),
                new Restaurant(3, "gdsddddddddddddddd", "avatar 1", "gdsgdrererr", new User(1)),
                new Restaurant(4, "nanangan", "avatar 1", "addrrr", new User(1))
        );
    }

    @Bean
    @Scope("prototype")
    public Scheme scheme() {
        Restaurant restaurant = new Restaurant(1);
        return new Scheme(100, 150, restaurant);
    }
}
