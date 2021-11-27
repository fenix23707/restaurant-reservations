package by.vsu.controller;

import by.vsu.model.Restaurant;
import by.vsu.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private RestaurantService restaurantService;

    @Autowired
    public void setRestaurantService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<Restaurant> all(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum) {
        return restaurantService.getAll(pageSize, pageNum);
    }

    @GetMapping("name/{name}")
    public List<Restaurant> allByName(@PathVariable("name") String name,
                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                      @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum) {
        return restaurantService.getAllByName(name, pageSize, pageNum);
    }

    @GetMapping("/address/{address}")
    public List<Restaurant> allByAddress(@PathVariable("address") String address,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                         @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum) {
        return restaurantService.getAllByAddress(address, pageSize, pageNum);
    }

    @GetMapping("/{id}")
    public Restaurant getById(@PathVariable("id") Integer id) {
        return restaurantService.getByUserId(id);
    }

    @PostMapping
    public ResponseEntity<Restaurant> newRestaurant(@RequestBody Restaurant restaurant) {
        ResponseEntity responseEntity;
        if (restaurant.getId() == null) {
            Restaurant created = restaurantService.save(restaurant);
            responseEntity = new ResponseEntity(created, HttpStatus.CREATED);
        } else {
            responseEntity = new ResponseEntity(HttpStatus.CONFLICT);
        }

        return responseEntity;
    }

    @PutMapping
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody Restaurant restaurant) {
        ResponseEntity<Restaurant> responseEntity;
        if (restaurant.getId() != null) {
            Restaurant updated = restaurantService.save(restaurant);
            responseEntity = new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        ResponseEntity responseEntity;
        if (restaurantService.delete(id)) {
            responseEntity = new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }
}
