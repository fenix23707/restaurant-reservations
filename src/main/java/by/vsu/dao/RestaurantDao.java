package by.vsu.dao;

import by.vsu.model.Restaurant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;
public interface RestaurantDao {
    @Select("SELECT id, name, adres, avatar, user_id FROM RESTAURANTS")
    @ResultMap("restaurantResultMap")
    List<Restaurant> findAll();

    @Select("SELECT id, name, adres, avatar, user_id FROM RESTAURANTS WHERE id = #{id}")
    @ResultMap("restaurantResultMap")
    Restaurant findById(@Param("id") Integer id);
}
