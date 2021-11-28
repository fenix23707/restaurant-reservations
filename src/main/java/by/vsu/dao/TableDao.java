package by.vsu.dao;

import by.vsu.model.Comparison;
import by.vsu.model.Position;
import by.vsu.model.Table;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TableDao {
    List<Table> findAll(@Param("size") int size, @Param("skip") int skip);

    List<Table> findAllByCapacity(@Param("capacity") int capacity,
                                  @Param("comparison") Comparison comparison,
                                  @Param("size") int size,
                                  @Param("skip") int skip);

    List<Table> findAllByWidthHeight(@Param("width") int width,
                                     @Param("height") int height,
                                     @Param("compWidth") Comparison compWidth,
                                     @Param("compHeight") Comparison compHeight,
                                     @Param("size") int size,
                                     @Param("skip") int skip);

    List<Table> findAllByPosition(@Param("position") Position position,
                                  @Param("compX") Comparison compX,
                                  @Param("compY") Comparison compY,
                                  @Param("size") int size,
                                  @Param("skip") int skip);

    List<Table> findAllBySchemeId(@Param("schemeId") Integer schemeId,
                                  @Param("size") int size,
                                  @Param("skip") int skip);

    Table findById(@Param("id") Integer id);

    void create(Table table);

    void update(Table table);

    int delete(Integer id);
}
