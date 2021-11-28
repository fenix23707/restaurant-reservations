package by.vsu.dao;

import by.vsu.model.Comparison;
import by.vsu.model.TableReservation;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TableReservationDao {
    List<TableReservation> findAll(@Param("size") int size, @Param("skip") int skip);

    List<TableReservation> findAllByDateBeginEnd(@Param("begin") Date begin,
                                                 @Param("end") Date end,
                                                 @Param("compBegin") Comparison compBegin,
                                                 @Param("compEnd") Comparison compEnd,
                                                 @Param("size") int size,
                                                 @Param("skip") int skip);

    List<TableReservation> findAllByCapacity(@Param("capacity") int capacity,
                                             @Param("comparison") Comparison comparison,
                                             @Param("size") int size,
                                             @Param("skip") int skip);

    List<TableReservation.Status> findAllByStatus(@Param("status") TableReservation.Status status,
                                                  @Param("size") int size,
                                                  @Param("skip") int skip);

    List<TableReservation> findAllByTableId(@Param("tableId") Integer tableId,
                                            @Param("size") int size,
                                            @Param("skip") int skip);

    List<TableReservation> findAllByUserId(@Param("userId") Integer userId,
                                           @Param("size") int size,
                                           @Param("skip") int skip);

    TableReservation findById(@Param("id") Integer id);

    void create(TableReservation tableReservation);

    void update(TableReservation tableReservation);

    int delete(@Param("id") Integer id);

}
