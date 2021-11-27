package by.vsu.controller;

import by.vsu.config.TestConfig;
import by.vsu.model.Restaurant;
import by.vsu.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Import(TestConfig.class)
public class RestaurantControllerTest implements ApplicationContextAware {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Autowired
    private ObjectMapper mapper;

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
    public void allTest() throws Exception {
        when(restaurantService.getAll(anyInt(), anyInt())).thenReturn(restaurants);

        mockMvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(restaurants)));
    }

    @Test
    public void allByNameTest() throws Exception {
        String name = "na";
        List<Restaurant> restaurantsByName = restaurants.stream()
                .filter(restaurant -> name.toLowerCase(Locale.ROOT).equals(restaurant.getName().toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
        when(restaurantService.getAllByName(anyString(), anyInt(), anyInt())).thenReturn(restaurantsByName);

        mockMvc.perform(get("/restaurants/name/" + name))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(restaurantsByName)));
    }

    @Test
    public void allByAddressTest() throws Exception {
        String address = "re";
        List<Restaurant> restaurantsByAddress = restaurants.stream()
                .filter(restaurant -> address.toLowerCase(Locale.ROOT).equals(restaurant.getAddress().toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
        when(restaurantService.getAllByAddress(anyString(), anyInt(), anyInt())).thenReturn(restaurantsByAddress);

        mockMvc.perform(get("/restaurants/address/" + address))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(restaurantsByAddress)));
    }

    @Test
    public void getByIdTest() throws Exception {
        Integer id = 1;
        Restaurant restaurant = getRestaurant();
        restaurant.setId(id);
        when(restaurantService.getByUserId(id)).thenReturn(restaurant);

        mockMvc.perform(get("/restaurants/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(restaurant)));
    }

    @Test
    public void newRestaurantCreatedTest() throws Exception {
        Integer id = 1;
        Restaurant response = getRestaurant();
        response.setId(id);
        Restaurant restaurant = getRestaurant();
        restaurant.setId(null);
        doAnswer(i -> {
            Restaurant r = i.getArgument(0);
            r.setId(id);
            return r;
        }).when(restaurantService).save(restaurant);

        mockMvc.perform(post("/restaurants")
                .content(mapper.writeValueAsString(restaurant))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @Test
    public void newRestaurantConflicktTest() throws Exception {
        Integer id = 1;
        Restaurant restaurant = getRestaurant();
        restaurant.setId(id);

        mockMvc.perform(post("/restaurants")
                .content(mapper.writeValueAsString(restaurant))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void updateRestaurantTest() throws Exception {
        Integer id = 1;
        Restaurant restaurant = getRestaurant();
        restaurant.setId(id);
        when(restaurantService.save(any(Restaurant.class))).thenReturn(restaurant);

        mockMvc.perform(put("/restaurants")
                .content(mapper.writeValueAsString(restaurant))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(restaurant)));
    }

    @Test
    public void updateBadRequestTest() throws Exception {
        Restaurant restaurant = getRestaurant();
        restaurant.setId(null);

        mockMvc.perform(put("/restaurants")
                .content(mapper.writeValueAsString(restaurant))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteNoContentTest() throws Exception {
        Integer id = 1;
        when(restaurantService.delete(anyInt())).thenReturn(true);

        mockMvc.perform(delete("/restaurants/" + id))
                .andExpect(status().isNoContent());
    }


    @Test
    public void deleteNotFoundTest() throws Exception {
        Integer id = 1;
        when(restaurantService.delete(anyInt())).thenReturn(false);

        mockMvc.perform(delete("/restaurants/" + id))
                .andExpect(status().isNotFound());
    }

    private Restaurant getRestaurant() {
        return context.getBean("restaurant", Restaurant.class);
    }
}