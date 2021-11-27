package by.vsu.dao;

import by.vsu.model.Restaurant;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RestaurantDao {
    @Select("SELECT id, name, adres, avatar, user_id " +
            "FROM restaurants " +
            "LIMIT #{size} OFFSET #{skip} ")
    @ResultMap("restaurantResultMap")
    List<Restaurant> findAll(@Param("size") int size, @Param("skip") int skip);

    @Select("SELECT id, name, adres, avatar, user_id " +
            "FROM restaurants " +
            "WHERE LOWER(name) LIKE \'%\' || LOWER(#{name}) || \'%\' " +
            "LIMIT #{size} OFFSET #{skip}")
    @ResultMap("restaurantResultMap")
    /**
     * Get list of restaurants by matching name. Name is matched using 'contains' and case-insensitive approach.
     * In case nothing was found, empty list is returned.
     * @param name Restaurants name or it's part.
     * @param size Number of restaurants to return.
     * @param skip Number of the restaurants to skip.
     * @return List of restaurants.
     */
    List<Restaurant> findByName(@Param("name") String name,
                                @Param("size") int size,
                                @Param("skip") int skip);

    @Select("SELECT id, name, adres, avatar, user_id " +
            "FROM restaurants " +
            "WHERE LOWER(adres) LIKE \'%\' || LOWER(#{address}) || \'%\' " +
            "LIMIT #{size} OFFSET #{skip}")
    @ResultMap("restaurantResultMap")
    /**
     * Get list of restaurants by matching address. address is matched using 'contains' and case-insensitive approach.
     * In case nothing was found, empty list is returned.
     * @param name Restaurants address or it's part.
     * @param size Number of restaurants to return.
     * @param skip Number of the restaurants to skip.
     * @return List of restaurants.
     */
    List<Restaurant> findByAddress(@Param("address") String address,
                                @Param("size") int size,
                                @Param("skip") int skip);

    @Select("SELECT id, name, adres, avatar, user_id " +
            "FROM restaurants " +
            "WHERE user_id = #{userId} " +
            "LIMIT #{size} OFFSET #{skip}")
    @ResultMap("restaurantResultMap")
    List<Restaurant> findByUserId(@Param("userId") Integer userId,
                                  @Param("size") int size,
                                  @Param("skip") int skip);

    @Select("SELECT id, name, adres, avatar, user_id " +
            "FROM restaurants " +
            "WHERE id = #{id}")
    @ResultMap("restaurantResultMap")
    Restaurant findById(@Param("id") Integer id);

    @Insert("INSERT INTO restaurants (name, adres, avatar, user_id) " +
            "VALUES(#{name}, #{address}, #{avatar}, #{user.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void create(Restaurant restaurant);

    @Update("UPDATE restaurants " +
            "SET name = #{name}, adres = #{address}, avatar = #{avatar}, user_id = #{user.id} " +
            "WHERE id = #{id}")
    void update(Restaurant restaurant);

    @Delete("DELETE FROM restaurants " +
            "WHERE id = #{id}")
    int delete(@Param("id") Integer id);

}
