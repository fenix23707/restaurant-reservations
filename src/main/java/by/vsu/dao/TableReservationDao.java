package by.vsu.dao;

import by.vsu.model.Comparison;
import by.vsu.model.TableReservation;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface TableReservationDao {
    @Select("SELECT id, datetime_begin, datetime_end, capacity, status, table_id, user_id " +
            "FROM book_tables " +
            "LIMIT #{size} OFFSET #{skip}")
    @ResultMap("tableReservationResultMap")
    List<TableReservation> findAll(@Param("size") int size, @Param("skip") int skip);

    @Select("SELECT id, datetime_begin, datetime_end, capacity, status, table_id, user_id " +
            "FROM book_tables " +
            "WHERE datetime_begin ${compBegin.value} #{begin} AND datetime_end ${compEnd.value} #{end} " +
            "LIMIT #{size} OFFSET #{skip}")
    @ResultMap("tableReservationResultMap")
    List<TableReservation> findAllByDateBeginEnd(@Param("begin") Date begin,
                                                 @Param("end") Date end,
                                                 @Param("compBegin") Comparison compBegin,
                                                 @Param("compEnd") Comparison compEnd,
                                                 @Param("size") int size,
                                                 @Param("skip") int skip);

    @Select("SELECT id, datetime_begin, datetime_end, capacity, status, table_id, user_id " +
            "FROM book_tables " +
            "WHERE  capacity ${comparison.value} #{capacity} " +
            "LIMIT #{size} OFFSET #{skip}")
    @ResultMap("tableReservationResultMap")
    List<TableReservation> findAllByCapacity(@Param("capacity") int capacity,
                                             @Param("comparison") Comparison comparison,
                                             @Param("size") int size,
                                             @Param("skip") int skip);

    @Select("SELECT id, datetime_begin, datetime_end, capacity, status, table_id, user_id " +
            "FROM book_tables " +
            "WHERE  status = #{status} " +
            "LIMIT #{size} OFFSET #{skip}")
    @ResultMap("tableReservationResultMap")
    List<TableReservation> findAllByStatus(@Param("status") TableReservation.Status status,
                                                  @Param("size") int size,
                                                  @Param("skip") int skip);

    @Select("SELECT id, datetime_begin, datetime_end, capacity, status, table_id, user_id " +
            "FROM book_tables " +
            "WHERE  table_id = #{tableId} " +
            "LIMIT #{size} OFFSET #{skip}")
    @ResultMap("tableReservationResultMap")
    List<TableReservation> findAllByTableId(@Param("tableId") Integer tableId,
                                            @Param("size") int size,
                                            @Param("skip") int skip);

    @Select("SELECT id, datetime_begin, datetime_end, capacity, status, table_id, user_id " +
            "FROM book_tables " +
            "WHERE  user_id = #{userId} " +
            "LIMIT #{size} OFFSET #{skip}")
    @ResultMap("tableReservationResultMap")
    List<TableReservation> findAllByUserId(@Param("userId") Integer userId,
                                           @Param("size") int size,
                                           @Param("skip") int skip);

    @Select("SELECT id, datetime_begin, datetime_end, capacity, status, table_id, user_id " +
            "FROM book_tables " +
            "WHERE  id = #{id} ")
    @ResultMap("tableReservationResultMap")
    TableReservation findById(@Param("id") Integer id);

    @Insert("INSERT INTO book_tables (datetime_begin, datetime_end, capacity, status, table_id, user_id) " +
            "VALUES( #{begin}, #{end}, #{capacity}, #{status}, #{table.id}, #{user.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void create(TableReservation tableReservation);

    @Update("UPDATE book_tables SET " +
            "datetime_begin=#{begin}, datetime_end=#{end}, capacity=#{capacity}, status=#{status}, table_id=#{table.id}, user_id=#{user.id} " +
            "WHERE id = #{id}")
    void update(TableReservation tableReservation);

    @Delete("DELETE FROM book_tables " +
            "WHERE id = #{id}")
    int delete(@Param("id") Integer id);

}
