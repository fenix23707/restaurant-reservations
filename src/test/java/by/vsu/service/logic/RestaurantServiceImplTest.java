package by.vsu.service.logic;

import by.vsu.config.TestConfig;
import by.vsu.dao.RestaurantDao;
import by.vsu.model.Restaurant;
import by.vsu.service.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class RestaurantServiceImplTest implements ApplicationContextAware {
    @Mock
    private RestaurantDao restaurantDao;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @Autowired
    private List<Restaurant> restaurants;

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllTest() {
        when(restaurantDao.findAll(anyInt(), anyInt())).thenReturn(restaurants);

        List<Restaurant> actual = restaurantService.getAll(restaurants.size(), 0);
        assertEquals(restaurants, actual);
    }

    @Test
    public void getAllByNameTest() {
        String name = "na";
        List<Restaurant> restaurantsByName = restaurants.stream()
                .filter(restaurant -> name.toLowerCase(Locale.ROOT).equals(restaurant.getName().toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
        when(restaurantDao.findByName(anyString(), anyInt(), anyInt())).thenReturn(restaurantsByName);
        List<Restaurant> actual = restaurantService.getAllByName(name, restaurantsByName.size(), 0);
        assertEquals(restaurantsByName, actual);
    }

    @Test
    public void getAllByAddressTest() {
        String address = "re";
        List<Restaurant> restaurantsByAddress = restaurants.stream()
                .filter(restaurant -> address.toLowerCase(Locale.ROOT).equals(restaurant.getAddress().toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
        when(restaurantDao.findByAddress(anyString(), anyInt(), anyInt())).thenReturn(restaurantsByAddress);
        List<Restaurant> actual = restaurantService.getAllByAddress(address, restaurantsByAddress.size(), 0);
        assertEquals(restaurantsByAddress, actual);
    }

    @Test
    public void getByUserIdTest() {
        Restaurant restaurant = getRestaurant();
        when(restaurantDao.findById(anyInt())).thenReturn(restaurant);
        Restaurant actual = restaurantService.getByUserId(1);
        assertEquals(restaurant, actual);
    }

    @Test
    public void saveNewObjectTest() {
        Integer id = 1;
        Restaurant restaurant = getRestaurant();
        restaurant.setId(null);

        doAnswer(i -> {
            Restaurant r = i.getArgument(0);
            r.setId(id);
            return null;
        }).when(restaurantDao).create(any(Restaurant.class));

        Restaurant actual = restaurantService.save(restaurant);
        restaurant.setId(id);
        assertEquals(restaurant, actual);
    }

    @Test
    public void saveExistObjectTest() {
        Integer id = 1;
        Restaurant restaurant = getRestaurant();
        restaurant.setId(id);
        when(restaurantDao.findById(id)).thenReturn(restaurant);

        Restaurant actual = restaurantService.save(restaurant);
        assertEquals(restaurant, actual);
    }

    @Test(expected = NotFoundException.class)
    public void saveNotExistObjectTest() {
        Integer id = 1;
        Restaurant restaurant = getRestaurant();
        restaurant.setId(id);
        when(restaurantDao.findById(id)).thenReturn(null);

        restaurantService.save(restaurant);
    }

    @Test
    public void deleteOneTest() {
        Integer id = 1;
        when(restaurantDao.delete(anyInt())).thenReturn(1);
        assertTrue(restaurantService.delete(id));
    }

    @Test
    public void deleteZeroTest() {
        Integer id = 1;
        when(restaurantDao.delete(anyInt())).thenReturn(0);
        assertFalse(restaurantService.delete(id));
    }

    private Restaurant getRestaurant() {
        return context.getBean("restaurant", Restaurant.class);
    }
}