package by.vsu.service;

import by.vsu.model.Comparison;
import by.vsu.model.Scheme;

import java.util.List;

public interface SchemeService {
    List<Scheme> getAll(int pageSize, int pageNum);

    List<Scheme> getAllByWidthHeight(int width, Comparison compWidth,
                                     int height, Comparison compHeight,
                                     int pageSize, int pageNum);

    List<Scheme> getAllBySquare(int square, Comparison comparison,
                                int pageSize, int pageNum);

    Scheme getById(Integer id);

    Scheme getByRestaurantId(Integer restaurantId);

    Scheme save(Scheme scheme);

    boolean delete(Integer id);
}
