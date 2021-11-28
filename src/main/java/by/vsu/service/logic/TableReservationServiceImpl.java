package by.vsu.service.logic;

import by.vsu.dao.TableReservationDao;
import by.vsu.model.Comparison;
import by.vsu.model.TableReservation;
import by.vsu.service.TableReservationService;
import by.vsu.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TableReservationServiceImpl implements TableReservationService {
    private TableReservationDao tableReservationDao;

    @Autowired
    public void setTableReservationDao(TableReservationDao tableReservationDao) {
        this.tableReservationDao = tableReservationDao;
    }

    @Override
    public List<TableReservation> findAll(int pageSize, int pageNum) {
        return tableReservationDao.findAll(pageSize, pageSize * pageNum);
    }

    @Override
    public List<TableReservation> findAllByDateBeginEnd(Date begin, Comparison compBegin, Date end, Comparison compEnd, int pageSize, int pageNum) {
        return tableReservationDao.findAllByDateBeginEnd(
                begin, end, compBegin, compEnd, pageSize, pageSize * pageNum
        );
    }

    @Override
    public List<TableReservation> findAllByCapacity(int capacity, Comparison comparison, int pageSize, int pageNum) {
        return tableReservationDao.findAllByCapacity(
                capacity, comparison, pageSize, pageSize * pageNum
        );
    }

    @Override
    public List<TableReservation> findAllByStatus(TableReservation.Status status, int pageSize, int pageNum) {
        return tableReservationDao.findAllByStatus(
                status, pageSize, pageSize * pageNum
        );
    }

    @Override
    public List<TableReservation> findAllByTableId(Integer tableId, int pageSize, int pageNum) {
        return tableReservationDao.findAllByTableId(
                tableId, pageSize, pageSize * pageNum
        );
    }

    @Override
    public List<TableReservation> findAllByUserId(Integer userId, int pageSize, int pageNum) {
        return tableReservationDao.findAllByUserId(
                userId, pageSize, pageSize * pageNum
        );
    }

    @Override
    public TableReservation findById(Integer id) {
        return tableReservationDao.findById(id);
    }

    @Override
    public TableReservation save(TableReservation reservation) {
        if (reservation.getId() == null) {
            tableReservationDao.create(reservation);
        } else {
            TableReservation old = tableReservationDao.findById(reservation.getId());
            if (old == null) {
                throw new NotFoundException("Table reservation with id = " + reservation.getId() + " not found.");
            }
            tableReservationDao.update(reservation);
        }
        return reservation;
    }

    @Override
    public boolean delete(Integer id) {
        return tableReservationDao.delete(id) != 0;
    }
}
