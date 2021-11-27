package by.vsu.service.logic;

import by.vsu.dao.RestaurantDao;
import by.vsu.model.Restaurant;
import by.vsu.service.RestaurantService;
import by.vsu.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private RestaurantDao restaurantDao;

    @Autowired
    public void setRestaurantDao(RestaurantDao restaurantDao) {
        this.restaurantDao = restaurantDao;
    }

    @Override
    public List<Restaurant> getAll(int pageSize, int pageNum) {
        return restaurantDao.findAll(pageSize, pageSize * pageNum);
    }

    @Override
    public List<Restaurant> getAllByName(String name, int pageSize, int pageNum) {
        return restaurantDao.findByName(name, pageSize, pageSize * pageNum);
    }

    @Override
    public List<Restaurant> getAllByAddress(String address, int pageSize, int pageNum) {
        return restaurantDao.findByAddress(address, pageSize, pageSize * pageNum);
    }

    @Override
    public Restaurant getByUserId(Integer id) {
        return restaurantDao.findById(id);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        if (restaurant.getId() == null) {
            restaurantDao.create(restaurant);
        } else {
            Restaurant old = restaurantDao.findById(restaurant.getId());
            if (old == null) {
                throw new NotFoundException("Restaurant with id = " + restaurant.getId() + " not found.");
            }
            restaurantDao.update(restaurant);
        }
        return restaurant;
    }

    @Override
    public boolean delete(Integer id) {
        return restaurantDao.delete(id) != 0;
    }
}
