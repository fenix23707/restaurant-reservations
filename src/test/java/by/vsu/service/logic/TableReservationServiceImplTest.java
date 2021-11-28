package by.vsu.service.logic;

import by.vsu.config.TestConfig;
import by.vsu.dao.TableReservationDao;
import by.vsu.model.TableReservation;
import by.vsu.service.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TableReservationServiceImplTest implements ApplicationContextAware {
    @Mock
    private TableReservationDao reservationDao;

    @InjectMocks
    private TableReservationServiceImpl reservationService;

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveNewObjectTest() {
        Integer id = 1;
        TableReservation reservation = getTableReservation();
        reservation.setId(null);

        doAnswer(iom -> {
            TableReservation tr = iom.getArgument(0);
            tr.setId(id);
            return null;
        }).when(reservationDao).create(reservation);

        TableReservation actual = reservationService.save(reservation);
        reservation.setId(id);
        assertEquals(reservation, actual);
    }

    @Test
    public void saveExistedObjectTest() {
        Integer id = 1;
        TableReservation reservation = getTableReservation();
        reservation.setId(id);
        when(reservationDao.findById(id)).thenReturn(new TableReservation());

        TableReservation actual = reservationService.save(reservation);
        assertEquals(reservation, actual);
    }

    @Test(expected = NotFoundException.class)
    public void saveNotExistedObjectTest() {
        Integer id = 1;
        TableReservation reservation = getTableReservation();
        reservation.setId(id);
        when(reservationDao.findById(id)).thenReturn(null);
        reservationService.save(reservation);
    }

    @Test
    public void deleteOneTest() {
        Integer id = 1;
        when(reservationDao.delete(id)).thenReturn(1);

        assertTrue(reservationService.delete(id));
    }

    @Test
    public void deleteZeroTest() {
        Integer id = 1;
        when(reservationDao.delete(id)).thenReturn(0);

        assertFalse(reservationService.delete(id));
    }

    private TableReservation getTableReservation() {
        return context.getBean("tableReservation", TableReservation.class);
    }
}