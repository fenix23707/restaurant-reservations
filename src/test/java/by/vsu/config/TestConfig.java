package by.vsu.config;

import by.vsu.model.*;
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

    @Bean
    public List<Scheme> schemes() {
        return Arrays.asList(
                new Scheme(1, 150, 50, new Restaurant(1)),
                new Scheme(2, 150, 100, new Restaurant(2)),
                new Scheme(3, 250, 100, new Restaurant(3)),
                new Scheme(4, 50, 50, new Restaurant(4))
        );
    }

    @Bean
    @Scope("prototype")
    public Table table() {
        Position position = new Position(20, 30);
        return new Table(4, 60, 40, position, new Scheme(1));
    }

    @Bean
    public List<Table> tables() {
        return Arrays.asList(
                new Table(1, 4, 100, 50, new Position(40, 40), new Scheme(1)),
                new Table(2, 4, 30, 50, new Position(40, 180), new Scheme(1)),
                new Table(3, 8, 100, 30, new Position(40, 40), new Scheme(1)),
                new Table(4, 6, 80, 80, new Position(40, 40), new Scheme(1)),
                new Table(5, 12, 20, 50, new Position(340, 40), new Scheme(1)),
                new Table(6, 2, 200, 150, new Position(80, 140), new Scheme(2)),
                new Table(7, 4, 120, 50, new Position(140, 40), new Scheme(3)),
                new Table(8, 9, 40, 50, new Position(40, 240), new Scheme(4)),
                new Table(9, 4, 50, 50, new Position(400, 440), new Scheme(2))
        );
    }
}
