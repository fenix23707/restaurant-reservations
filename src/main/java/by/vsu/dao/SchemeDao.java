package by.vsu.dao;

import by.vsu.model.Comparison;
import by.vsu.model.Scheme;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SchemeDao {
    @Select("SELECT id, width, height, restaurant_id " +
            "FROM schemes " +
            "LIMIT #{size} OFFSET #{skip}")
    @ResultMap("schemeResultMap")
    List<Scheme> findAll(@Param("size") int size, @Param("skip") int skip);

    @Select("SELECT id, width, height, restaurant_id " +
            "FROM schemes " +
            "WHERE width ${compWidth.value} #{width} AND height ${compHeight.value} #{height} " +
            "LIMIT #{size} OFFSET #{skip}")
    @ResultMap("schemeResultMap")
    List<Scheme> findAllByWidthHeight(@Param("width") int width,
                                      @Param("height") int height,
                                      @Param("compWidth") Comparison compWidth,
                                      @Param("compHeight") Comparison compHeight,
                                      @Param("size") int size,
                                      @Param("skip") int skip);

    @Select("SELECT id, width, height, restaurant_id " +
            "FROM schemes " +
            "WHERE width * height ${comparison.value} #{square} " +
            "LIMIT #{size} OFFSET #{skip}")
    @ResultMap("schemeResultMap")
    List<Scheme> findAllBySquare(@Param("square") int square,
                                 @Param("comparison") Comparison comparison,
                                 @Param("size") int size,
                                 @Param("skip") int skip);

    @Select("SELECT id, width, height, restaurant_id " +
            "FROM schemes " +
            "WHERE id = #{id}")
    @ResultMap("schemeResultMap")
    Scheme findById(@Param("id") Integer id);

    @Select("SELECT id, width, height, restaurant_id " +
            "FROM schemes " +
            "WHERE restaurant_id = #{restaurantId}")
    @ResultMap("schemeResultMap")
    Scheme findByRestaurantId(@Param("restaurantId") Integer restaurantId);

    @Insert("INSERT INTO schemes (width, height, restaurant_id) " +
            "VALUES (#{width}, #{height}, #{restaurant.id}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void create(Scheme scheme);

    @Update("UPDATE schemes " +
            "SET width = #{width}, height = #{height}, restaurant_id = #{restaurant.id} " +
            "WHERE id = #{id}")
    void update(Scheme scheme);

    @Delete("DELETE FROM schemes " +
            "WHERE id = #{id}")
    int delete(Integer id);
}
