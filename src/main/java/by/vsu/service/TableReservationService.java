package by.vsu.service;

import by.vsu.model.Comparison;
import by.vsu.model.TableReservation;

import java.util.Date;
import java.util.List;

public interface TableReservationService {
    List<TableReservation> findAll(int pageSize, int pageNum);

    List<TableReservation> findAllByDateBeginEnd(Date begin, Comparison compBegin,
                                                 Date end, Comparison compEnd,
                                                 int pageSize, int pageNum);

    List<TableReservation> findAllByCapacity(
            int capacity, Comparison comparison, int pageSize, int pageNum
    );

    List<TableReservation> findAllByStatus(
            TableReservation.Status status, int pageSize, int pageNum
    );

    List<TableReservation> findAllByTableId(
            Integer tableId, int pageSize, int pageNum
    );

    List<TableReservation> findAllByUserId(
            Integer userId, int pageSize, int pageNum
    );

    TableReservation findById(Integer id);

    TableReservation save(TableReservation reservation);

    boolean delete(Integer id);
}
