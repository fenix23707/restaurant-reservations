package by.vsu.controller;

import by.vsu.config.TestConfig;
import by.vsu.model.Comparison;
import by.vsu.model.TableReservation;
import by.vsu.service.TableReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(TableReservationController.class)
@Import(TestConfig.class)
public class TableReservationControllerTest implements ApplicationContextAware {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TableReservationService reservationService;

    @Autowired
    private List<TableReservation> tableReservations;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SimpleDateFormat dateFormat;

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
    public void allTest() throws Exception {
        when(reservationService.findAll(10, 0)).thenReturn(tableReservations);

        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(tableReservations)));
    }

    @Test
    public void allByDateBeginEndTest() throws Exception {
        Date begin = dateFormat.parse(dateFormat.format(new Date()));
        Date end = new Date(begin.getTime() + 1000 * 60 * 60 * 24);
        Comparison compBegin = Comparison.GTE;
        Comparison compEnd = Comparison.GTE;
        when(reservationService.findAllByDateBeginEnd(begin, compBegin, end, compEnd, 10, 0))
                .thenReturn(tableReservations);

        mockMvc.perform(get("/reservations/date/beginend")
                .param("begin", dateFormat.format(begin))
                .param("end", dateFormat.format(end)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(tableReservations)));
    }

    @Test
    public void allByCapacityTest() throws Exception {
        int capacity = 4;
        Comparison comparison = Comparison.EQ;
        when(reservationService.findAllByCapacity(capacity, comparison, 10, 0))
                .thenReturn(tableReservations);

        mockMvc.perform(get("/reservations/capacity")
                .param("capacity", Integer.toString(capacity))
                .param("comparison", comparison.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(tableReservations)));
    }

    @Test
    public void allByStatusTest() throws Exception {
        TableReservation.Status defaultStatus = TableReservation.Status.BOOKED;
        when(reservationService.findAllByStatus(defaultStatus, 10, 0))
                .thenReturn(tableReservations);

        mockMvc.perform(get("/reservations/status"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(tableReservations)));
    }

    @Test
    public void allByTableIdTest() throws Exception {
        Integer tableId = 1;
        when(reservationService.findAllByTableId(tableId, 10, 0))
                .thenReturn(tableReservations);

        mockMvc.perform(get("/reservations/table/" + tableId))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(tableReservations)));
    }

    @Test
    public void allByUserIdTest() throws Exception {
        Integer userId = 1;
        when(reservationService.findAllByUserId(userId, 10, 0))
                .thenReturn(tableReservations);

        mockMvc.perform(get("/reservations/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(tableReservations)));

    }

    @Test
    public void getByIdTest() throws Exception {
        Integer id = 1;
        TableReservation reservation = getTableReservation();
        when(reservationService.findById(id)).thenReturn(reservation);

        mockMvc.perform(get("/reservations/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(reservation)));
    }

    @Test
    public void newTableReservationCreatedTest() throws Exception {
        TableReservation reservation = getTableReservation();
        reservation.setId(null);
        Integer id = 1;
        doAnswer(iom -> {
            TableReservation tr = iom.getArgument(0);
            tr.setId(id);
            return tr;
        }).when(reservationService).save(any(TableReservation.class));
        TableReservation response = getTableReservation();
        response.setId(id);

        mockMvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reservation)))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @Test
    public void newTableReservationConflictTest() throws Exception {
        TableReservation reservation = getTableReservation();
        reservation.setId(1);

        mockMvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reservation)))
                .andExpect(status().isConflict());
    }

    @Test
    public void updateTableReservationOkTest() throws Exception {
        Integer id = 1;
        TableReservation reservation = getTableReservation();
        reservation.setId(id);
        when(reservationService.save(any(TableReservation.class)))
                .thenReturn(reservation);

        mockMvc.perform(put("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reservation)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(reservation)));
    }

    @Test
    public void updateTableReservationBadRequestTest() throws Exception {
        TableReservation reservation = getTableReservation();
        reservation.setId(null);

        mockMvc.perform(put("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reservation)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteNoContentTest() throws Exception {
        Integer id = 1;
        when(reservationService.delete(id)).thenReturn(true);

        mockMvc.perform(delete("/reservations/" + id))
                .andExpect(status().isNoContent());
    }


    @Test
    public void deleteNotFoundTest() throws Exception {
        Integer id = 1;
        when(reservationService.delete(id)).thenReturn(false);

        mockMvc.perform(delete("/reservations/" + id))
                .andExpect(status().isNotFound());
    }

    private TableReservation getTableReservation() {
        return context.getBean("tableReservation", TableReservation.class);
    }
}