package by.vsu.dao;

import by.vsu.config.AppConfig;
import by.vsu.model.Restaurant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class RestaurantDaoTest {
    private RestaurantDao restaurantDao;

    @Autowired
    public void setRestaurantDao(RestaurantDao restaurantDao) {
        this.restaurantDao = restaurantDao;
    }

    @Test
    public void findAllTest() {
        List<Restaurant> restaurants = restaurantDao.findAll();
        assertEquals(5, restaurants.size());
    }

    @Test
    public void findByIdExistTest() {
        Restaurant restaurant = restaurantDao.findById(1);
        assertNotNull(restaurant);
    }

    @Test
    public void findByIdFieldsNotNullTest() {
        Restaurant restaurant = restaurantDao.findById(1);
        assertNotNull(restaurant.getId());
        assertNotNull(restaurant.getAdres());
        assertNotNull(restaurant.getAvatar());
        assertNotNull(restaurant.getName());
        assertNotNull(restaurant.getUser());
        assertNotNull(restaurant.getUser().getId());
    }
}