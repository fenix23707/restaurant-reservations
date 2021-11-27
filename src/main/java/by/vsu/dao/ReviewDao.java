package by.vsu.dao;

import by.vsu.model.Review;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ReviewDao {
    @Select("SELECT id, rate, review, date, user_id, restaurant_id " +
            "FROM reviews " +
            "LIMIT #{size} OFFSET #{skip} ")
    @ResultMap("reviewResultMap")
    List<Review> findAll(@Param("size") int size, @Param("skip") int skip);

    @Select("SELECT id, rate, review, date, user_id, restaurant_id " +
            "FROM reviews " +
            "WHERE id = #{id}")
    @ResultMap("reviewResultMap")
    Review findById(@Param("id") Integer id);

    @Select("SELECT id, rate, review, date, user_id, restaurant_id " +
            "FROM reviews " +
            "WHERE restaurant_id = #{restaurantId} " +
            "LIMIT #{size} OFFSET #{skip} ")
    @ResultMap("reviewResultMap")
    List<Review> findByRestaurantId(@Param("restaurantId") Integer restaurantId,
                                    @Param("size") int size,
                                    @Param("skip") int skip);

    @Select("SELECT id, rate, review, date, user_id, restaurant_id " +
            "FROM reviews " +
            "WHERE user_id = #{userId} " +
            "LIMIT #{size} OFFSET #{skip} ")
    @ResultMap("reviewResultMap")
    List<Review> findByUserId(@Param("userId") Integer userId,
                              @Param("size") int size,
                              @Param("skip") int skip);

    @Insert("INSERT INTO reviews (rate, review, date, user_id, restaurant_id) " +
            "VALUES(#{rate}, #{text}, #{date}, #{user.id}, #{restaurant.id}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void create(Review review);

    @Update("UPDATE reviews " +
            "SET rate=#{rate}, review=#{text}, date=#{date}, user_id=#{user.id}, restaurant_id=#{restaurant.id}")
    void update(Review review);

    @Delete("DELETE FROM reviews " +
            "WHERE id = #{id}")
    int delete(@Param("id") Integer id);
}
