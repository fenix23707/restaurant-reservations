package by.vsu.controller;

import by.vsu.model.Comparison;
import by.vsu.model.Position;
import by.vsu.model.Table;
import by.vsu.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tables")
public class TableController {
    private TableService tableService;

    @Autowired
    public void setTableService(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping
    public List<Table> all(@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                           @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
        return tableService.getAll(pageSize, pageNum);
    }

    @GetMapping("/capacity")
    public List<Table> allByCapacity(@RequestParam(value = "capacity", defaultValue = "4") int capacity,
                                     @RequestParam(value = "comparison", defaultValue = "EQ") Comparison comparison,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                     @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
        return tableService.getAllByCapacity(capacity, comparison, pageSize, pageNum);
    }

    @GetMapping("/widthheight")
    public List<Table> allByWidthHeight(@RequestParam(value = "width", defaultValue = "60") int width,
                                        @RequestParam(value = "compWidth", defaultValue = "EQ") Comparison compWidth,
                                        @RequestParam(value = "height", defaultValue = "60") int height,
                                        @RequestParam(value = "compHeight", defaultValue = "EQ") Comparison compHeight,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                        @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
        return tableService.getAllByWidthHeight(width, compWidth, height, compHeight, pageSize, pageNum);
    }

    @GetMapping("/position")
    public List<Table> allByPosition(@RequestParam(value = "x", defaultValue = "0") int x,
                                     @RequestParam(value = "compX", defaultValue = "EQ") Comparison compX,
                                     @RequestParam(value = "y", defaultValue = "0") int y,
                                     @RequestParam(value = "compY", defaultValue = "EQ") Comparison compY,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                     @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
        return tableService.getAllByPosition(new Position(x, y), compX, compY, pageSize, pageNum);
    }

    @GetMapping("/scheme/{schemeId}")
    public List<Table> allBySchemeId(@PathVariable("schemeId") Integer schemeId,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                     @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
        return tableService.getAllBySchemeId(schemeId, pageSize, pageNum);
    }

    @GetMapping("/{id}")
    public Table getById(@PathVariable("id") Integer id) {
        return tableService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Table> newTable(@RequestBody Table table) {
        ResponseEntity responseEntity;
        if (table.getId() == null) {
            Table created = tableService.save(table);
            responseEntity = new ResponseEntity(created, HttpStatus.CREATED);
        } else {
            responseEntity = new ResponseEntity(HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @PutMapping
    public ResponseEntity<Table> updateTable(@RequestBody Table table) {
        ResponseEntity responseEntity;
        if (table.getId() != null) {
            Table updated = tableService.save(table);
            responseEntity = new ResponseEntity(updated, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        ResponseEntity responseEntity;
        if (tableService.delete(id)) {
            responseEntity = new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }
}
