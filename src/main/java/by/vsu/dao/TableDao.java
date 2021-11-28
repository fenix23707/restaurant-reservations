package by.vsu.dao;

import by.vsu.model.Comparison;
import by.vsu.model.Position;
import by.vsu.model.Table;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TableDao {
    @Select("SELECT id, capacity, width, height, x, y, scheme_id " +
            "FROM tables " +
            "LIMIT #{size} OFFSET #{skip}")
    @ResultMap("tableResultMap")
    List<Table> findAll(@Param("size") int size, @Param("skip") int skip);

    @Select("SELECT id, capacity, width, height, x, y, scheme_id " +
            "FROM tables " +
            "WHERE capacity ${comparison.value} #{capacity} " +
            "LIMIT #{size} OFFSET #{skip}")
    @ResultMap("tableResultMap")
    List<Table> findAllByCapacity(@Param("capacity") int capacity,
                                  @Param("comparison") Comparison comparison,
                                  @Param("size") int size,
                                  @Param("skip") int skip);

    @Select("SELECT id, capacity, width, height, x, y, scheme_id " +
            "FROM tables " +
            "WHERE width ${compWidth.value} #{width} AND height ${compHeight.value} #{height} " +
            "LIMIT #{size} OFFSET #{skip}")
    @ResultMap("tableResultMap")
    List<Table> findAllByWidthHeight(@Param("width") int width,
                                     @Param("height") int height,
                                     @Param("compWidth") Comparison compWidth,
                                     @Param("compHeight") Comparison compHeight,
                                     @Param("size") int size,
                                     @Param("skip") int skip);

    @Select("SELECT id, capacity, width, height, x, y, scheme_id " +
            "FROM tables " +
            "WHERE x ${compX.value} #{position.x} AND y ${compY.value} #{position.y} " +
            "LIMIT #{size} OFFSET #{skip}")
    @ResultMap("tableResultMap")
    List<Table> findAllByPosition(@Param("position") Position position,
                                  @Param("compX") Comparison compX,
                                  @Param("compY") Comparison compY,
                                  @Param("size") int size,
                                  @Param("skip") int skip);

    @Select("SELECT id, capacity, width, height, x, y, scheme_id " +
            "FROM tables " +
            "WHERE scheme_id = #{schemeId} " +
            "LIMIT #{size} OFFSET #{skip}")
    @ResultMap("tableResultMap")
    List<Table> findAllBySchemeId(@Param("schemeId") Integer schemeId,
                                  @Param("size") int size,
                                  @Param("skip") int skip);

    @Select("SELECT id, capacity, width, height, x, y, scheme_id " +
            "FROM tables " +
            "WHERE id = #{id} ")
    @ResultMap("tableResultMap")
    Table findById(@Param("id") Integer id);

    @Insert("INSERT INTO tables (capacity, width, height, x, y, scheme_id) " +
            "VALUES(#{capacity}, #{width}, #{height}, #{position.x}, #{position.y}, #{scheme.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void create(Table table);

    @Update("UPDATE tables " +
            "SET capacity=#{capacity}, width=#{width}, height=#{height}, x=#{position.x}, y=#{position.y}, scheme_id=#{scheme.id} " +
            "WHERE id = #{id}")
    void update(Table table);

    @Delete("DELETE FROM tables " +
            "WHERE id = #{id}")
    int delete(@Param("id") Integer id);
}
