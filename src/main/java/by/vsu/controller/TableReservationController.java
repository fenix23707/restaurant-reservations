package by.vsu.controller;

import by.vsu.model.Comparison;
import by.vsu.model.TableReservation;
import by.vsu.service.TableReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class TableReservationController {
    private TableReservationService reservationService;

    @Autowired
    public void setReservationService(TableReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<TableReservation> all(@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                      @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
        return reservationService.findAll(pageSize, pageNum);
    }

    @GetMapping("/date/beginend")
    public List<TableReservation> allByDateBeginEnd(@RequestParam(value = "begin", required = false) @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm") Date begin,
                                                    @RequestParam(value = "end", required = false) @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm") Date end,
                                                    @RequestParam(value = "compBegin", defaultValue = "GTE") Comparison compBegin,
                                                    @RequestParam(value = "compEnd", defaultValue = "GTE") Comparison compEnd,
                                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                    @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
        if (begin == null) {
            begin = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        if (end == null) {
            end = new Date(begin.getTime() + 24 * 60 * 60 * 1000);
        }
        return reservationService.findAllByDateBeginEnd(begin, compBegin, end, compEnd, pageSize, pageNum);
    }

    @GetMapping("/capacity")
    public List<TableReservation> allByCapacity(@RequestParam(value = "capacity", defaultValue = "4") int capacity,
                                                @RequestParam(value = "comparison", defaultValue = "EQ") Comparison comparison,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
        return reservationService.findAllByCapacity(capacity, comparison, pageSize, pageNum);
    }

    @GetMapping("/status")
    public List<TableReservation> allByStatus(@RequestParam(value = "status", defaultValue = "BOOKED") TableReservation.Status status,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                              @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
        return reservationService.findAllByStatus(status, pageSize, pageNum);
    }

    @GetMapping("/table/{tableId}")
    public List<TableReservation> allByTableId(@PathVariable(value = "tableId") Integer tableId,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                               @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
        return reservationService.findAllByTableId(tableId, pageSize, pageNum);
    }

    @GetMapping("/user/{userId}")
    public List<TableReservation> allByUserId(@PathVariable(value = "userId") Integer userId,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                               @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
        return reservationService.findAllByUserId(userId, pageSize, pageNum);
    }

    @GetMapping("/{id}")
    public TableReservation getById(@PathVariable("id") Integer id) {
        return reservationService.findById(id);
    }

    @PostMapping
    public ResponseEntity<TableReservation> newTableReservation(@RequestBody TableReservation reservation) {
        ResponseEntity responseEntity;
        if (reservation.getId() == null) {
            TableReservation created = reservationService.save(reservation);
            responseEntity = new ResponseEntity(created, HttpStatus.CREATED);
        } else {
            responseEntity = new ResponseEntity(HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @PutMapping
    public ResponseEntity<TableReservation> updateTableReservation(@RequestBody TableReservation reservation) {
        ResponseEntity responseEntity;
        if (reservation.getId() != null) {
            TableReservation updated = reservationService.save(reservation);
            responseEntity = new ResponseEntity(updated, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        ResponseEntity responseEntity;
        if (reservationService.delete(id)) {
            responseEntity = new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

}
