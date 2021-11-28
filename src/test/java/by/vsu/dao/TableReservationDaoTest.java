package by.vsu.dao;

import by.vsu.config.TestConfig;
import by.vsu.model.Comparison;
import by.vsu.model.TableReservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TableReservationDaoTest implements ApplicationContextAware {
    private final static int TABLE_RESERVATIONS_SIZE = 5;

    @Autowired
    private TableReservationDao tableReservationDao;

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Test
    public void findAllTest() {
        List<TableReservation> tableReservations = tableReservationDao.findAll(TABLE_RESERVATIONS_SIZE, 0);

        assertEquals(TABLE_RESERVATIONS_SIZE, tableReservations.size());
        assertTrue(tableReservations.stream().allMatch(this::mainFieldsCorrect));
    }

    @Test
    public void findAllByDateBeginEnd() {
        Date begin = new GregorianCalendar(2020, 9, 8).getTime();
        Date end = new GregorianCalendar(2022, 9, 8).getTime();
        Comparison compBegin = Comparison.GTE;
        Comparison compEnd = Comparison.LTE;

        List<TableReservation> tableReservations = tableReservationDao.findAllByDateBeginEnd(
                begin, end,
                compBegin, compEnd,
                TABLE_RESERVATIONS_SIZE, 0
        );

        assertEquals(TABLE_RESERVATIONS_SIZE, tableReservations.size());
        assertTrue(tableReservations.stream().allMatch(this::mainFieldsCorrect));
        assertTrue(tableReservations.stream().allMatch(tr -> tr.getBegin().compareTo(begin) >= 0 && tr.getEnd().compareTo(end) <= 0));
    }

    @Test
    public void findAllByCapacityTest() {
        int capacity = 2;
        Comparison comparison = Comparison.EQ;
        List<TableReservation> tableReservations = tableReservationDao.findAllByCapacity(
                capacity, comparison, TABLE_RESERVATIONS_SIZE, 0);

        assertEquals(5, tableReservations.size());
        assertTrue(tableReservations.stream().allMatch(this::mainFieldsCorrect));
        assertTrue(tableReservations.stream().allMatch(tr -> tr.getCapacity() == capacity));
    }

    @Test
    public void findAllByStatusTest() {
        TableReservation.Status status = TableReservation.Status.BOOKED;
        List<TableReservation> tableReservations = tableReservationDao.findAllByStatus(
                status, TABLE_RESERVATIONS_SIZE, 0);

        assertEquals(5, tableReservations.size());
        assertTrue(tableReservations.stream().allMatch(this::mainFieldsCorrect));
        assertTrue(tableReservations.stream().allMatch(tr -> tr.getStatus() == status));
    }

    @Test
    public void findAllByTableIdTest() {
        Integer tableId = 1;
        List<TableReservation> tableReservations = tableReservationDao.findAllByTableId(
                tableId, TABLE_RESERVATIONS_SIZE, 0);

        assertEquals(5, tableReservations.size());
        assertTrue(tableReservations.stream().allMatch(this::mainFieldsCorrect));
        assertTrue(tableReservations.stream().allMatch(tr -> tr.getTable().getId() == tableId));
    }

    @Test
    public void findAllByUserId() {
        Integer userId = 6;
        List<TableReservation> tableReservations = tableReservationDao.findAllByUserId(
                userId, TABLE_RESERVATIONS_SIZE, 0);

        assertEquals(1, tableReservations.size());
        assertTrue(tableReservations.stream().allMatch(this::mainFieldsCorrect));
        assertTrue(tableReservations.stream().allMatch(tr -> tr.getUser().getId() == userId));
    }

    @Test
    public void findByIdTest() {
        Integer id = 1;
        TableReservation tableReservation = tableReservationDao.findById(id);

        assertEquals(id, tableReservation.getId());
        assertTrue(mainFieldsCorrect(tableReservation));
    }

    @Test
    @Transactional
    public void createChangeSizeTest() {
        assertEquals(TABLE_RESERVATIONS_SIZE, tableReservationDao.findAll(TABLE_RESERVATIONS_SIZE, 0).size());
        TableReservation tableReservation = getTableReservation();
        tableReservationDao.create(tableReservation);
        int expectedSize = TABLE_RESERVATIONS_SIZE + 1;
        assertEquals(expectedSize, tableReservationDao.findAll(expectedSize, 0).size());
    }

    @Test
    @Transactional
    public void createAutogeneratedIdTest() {
        TableReservation tableReservation = getTableReservation();
        tableReservation.setId(null);
        tableReservationDao.create(tableReservation);
        assertNotNull(tableReservation.getId());
    }

    @Test
    @Transactional
    public void update() {
        Integer id = 1;
        TableReservation reservation = tableReservationDao.findById(id);
        TableReservation newReservation = getTableReservation();
        newReservation.setId(id);
        reservation.setCapacity(newReservation.getCapacity());
        reservation.setBegin(newReservation.getBegin());
        reservation.setEnd(newReservation.getEnd());
        reservation.setTable(newReservation.getTable());
        reservation.setUser(newReservation.getUser());
        reservation.setStatus(newReservation.getStatus());
        tableReservationDao.update(reservation);

        TableReservation actual = tableReservationDao.findById(id);
        assertEquals(newReservation, actual);
    }

    @Test
    @Transactional
    public void delete() {
        assertEquals(TABLE_RESERVATIONS_SIZE, tableReservationDao.findAll(TABLE_RESERVATIONS_SIZE, 0).size());
        tableReservationDao.delete(1);
        int expectedSize = TABLE_RESERVATIONS_SIZE - 1;
        assertEquals(expectedSize, tableReservationDao.findAll(TABLE_RESERVATIONS_SIZE, 0).size());
    }

    private boolean mainFieldsCorrect(TableReservation tableReservation) {
        return tableReservation.getTable().getId() != null &&
                tableReservation.getUser().getId() != null &&
                tableReservation.getId() != null &&
                tableReservation.getBegin() != null &&
                tableReservation.getEnd() != null &&
                tableReservation.getStatus() != null &&
                tableReservation.getCapacity() > 0;
    }

    private TableReservation getTableReservation() {
        return context.getBean("tableReservation", TableReservation.class);
    }
}