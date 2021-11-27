package by.vsu.config;

import by.vsu.model.Restaurant;
import by.vsu.model.Review;
import by.vsu.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

@Configuration()
@ContextConfiguration(classes = AppConfig.class)
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
}
