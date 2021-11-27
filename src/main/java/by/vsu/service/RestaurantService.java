package by.vsu.service;

import by.vsu.model.Restaurant;

import java.util.List;

public interface RestaurantService {
    List<Restaurant> getAll(int pageSize, int pageNum);

    List<Restaurant> getAllByName(String name, int pageSize, int pageNum);

    List<Restaurant> getAllByAddress(String address, int pageSize, int pageNum);

    Restaurant getByUserId(Integer id);

    Restaurant save(Restaurant restaurant);

    boolean delete(Integer id);
}
