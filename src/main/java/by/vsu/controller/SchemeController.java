package by.vsu.controller;

import by.vsu.model.Comparison;
import by.vsu.model.Scheme;
import by.vsu.service.SchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schemes")
public class SchemeController {
    private SchemeService schemeService;

    @Autowired
    public void setSchemeService(SchemeService schemeService) {
        this.schemeService = schemeService;
    }

    @GetMapping
    public List<Scheme> all(@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                            @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
        return schemeService.getAll(pageSize, pageNum);
    }

    @GetMapping("/widthheight")
    public List<Scheme> allByWidthHeight(@RequestParam("width") int width,
                                         @RequestParam("compWidth")Comparison compWidth,
                                         @RequestParam("height") int height,
                                         @RequestParam("compHeight")Comparison compHeight,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
        return schemeService.getAllByWidthHeight(width, compWidth,height , compHeight, pageSize, pageNum);
    }

    @GetMapping("/square")
    public List<Scheme> allBySquare(@RequestParam("square") int square,
                                         @RequestParam("comparison")Comparison comparison,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
        return schemeService.getAllBySquare(square, comparison, pageSize, pageNum);
    }

    @GetMapping("/{id}")
    public Scheme getById(@PathVariable("id") Integer id) {
        return schemeService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Scheme> newScheme(@RequestBody Scheme scheme) {
        ResponseEntity responseEntity;
        if (scheme.getId() == null)  {
            Scheme created = schemeService.save(scheme);
            responseEntity = new ResponseEntity(created, HttpStatus.CREATED);
        } else {
            responseEntity = new ResponseEntity(HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @PutMapping
    public ResponseEntity<Scheme> updateScheme(@RequestBody Scheme scheme) {
        ResponseEntity responseEntity;
        if (scheme.getId() != null) {
            Scheme updated = schemeService.save(scheme);
            responseEntity = new ResponseEntity(updated, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        ResponseEntity responseEntity;
        if (schemeService.delete(id)) {
            responseEntity = new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }
}
