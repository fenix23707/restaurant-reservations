package by.vsu.controller;

import by.vsu.config.TestConfig;
import by.vsu.model.Comparison;
import by.vsu.model.Position;
import by.vsu.model.Table;
import by.vsu.service.TableService;
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

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TableController.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Import(TestConfig.class)
public class TableControllerTest implements ApplicationContextAware {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TableService tableService;

    @Autowired
    private List<Table> tables;

    @Autowired
    private ObjectMapper mapper;

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
        when(tableService.getAll(anyInt(), anyInt())).thenReturn(tables);

        mockMvc.perform(get("/tables"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(tables)));
    }

    @Test
    public void allByCapacityTest() throws Exception {
        int capacity = 4;
        Comparison comparison = Comparison.EQ;
        List<Table> tablesByCapacity = tables.stream()
                .filter(table -> table.getCapacity() == capacity)
                .collect(Collectors.toList());
        when(tableService.getAllByCapacity(capacity, comparison, 10, 0)).thenReturn(tablesByCapacity);

        mockMvc.perform(get("/tables/capacity"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(tablesByCapacity)));
    }

    @Test
    public void allByWidthHeightTest() throws Exception {
        int width = 60;
        int height = 60;
        Comparison compWidth = Comparison.EQ;
        Comparison compHeight = Comparison.EQ;
        int pageSize = 10;
        int pageNum = 0;
        when(tableService.getAllByWidthHeight(width, compWidth, height, compHeight, pageSize, pageNum))
                .thenReturn(tables);

        mockMvc.perform(get("/tables/widthheight")
                .param("width", Integer.toString(width))
                .param("height", Integer.toString(height))
                .param("compWidth", compWidth.toString())
                .param("compHeight", compHeight.toString())
                .param("pageSize", Integer.toString(pageSize))
                .param("pageNum", Integer.toString(pageNum)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(tables)));
    }

    @Test
    public void allByPositionTest() throws Exception {
        Position position = new Position(0, 0);
        Comparison compX = Comparison.EQ;
        Comparison compY = Comparison.EQ;
        int pageSize = 10;
        int pageNum = 0;
        when(tableService.getAllByPosition(position, compX, compY, pageSize, pageNum))
                .thenReturn(tables);

        mockMvc.perform(get("/tables/position")
                .param("x", Integer.toString(position.getX()))
                .param("y", Integer.toString(position.getY()))
                .param("compX", compX.toString())
                .param("compY", compY.toString())
                .param("pageSize", Integer.toString(pageSize))
                .param("pageNum", Integer.toString(pageNum)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(tables)));
    }

    @Test
    public void allBySchemeIdTest() throws Exception {
        Integer schemeId = 1;
        int pageSize = 10;
        int pageNum = 0;
        when(tableService.getAllBySchemeId(schemeId, pageSize, pageNum))
                .thenReturn(tables);

        mockMvc.perform(get("/tables/scheme/" + schemeId)
                .param("pageSize", Integer.toString(pageSize))
                .param("pageNum", Integer.toString(pageNum)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(tables)));
    }

    @Test
    public void getByIdTest() throws Exception {
        Integer id = 1;
        Table table = getTable();
        table.setId(id);
        when(tableService.getById(id)).thenReturn(table);

        mockMvc.perform(get("/tables/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(table)));
    }

    @Test
    public void newTableCreatedTest() throws Exception {
        Integer id = 1;
        Table response = getTable();
        response.setId(id);
        Table table = getTable();
        table.setId(null);

        doAnswer(iom -> {
            Table t = iom.getArgument(0);
            t.setId(id);
            return t;
        }).when(tableService).save(table);

        mockMvc.perform(post("/tables")
                .content(mapper.writeValueAsString(table))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @Test
    public void newTableConflictTest() throws Exception {
        Integer id = 1;
        Table table = getTable();
        table.setId(id);

        mockMvc.perform(post("/tables")
                .content(mapper.writeValueAsString(table))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }


    @Test
    public void updateTableOkTest() throws Exception{
        Integer id = 1;
        Table table = getTable();
        table.setId(id);

        when(tableService.save(table)).thenReturn(table);

        mockMvc.perform(put("/tables")
                .content(mapper.writeValueAsString(table))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(table)));
    }

    @Test
    public void updateTableBadRequestTest() throws Exception{
        Table table = getTable();
        table.setId(null);

        mockMvc.perform(put("/tables")
                .content(mapper.writeValueAsString(table))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteNoContentTest() throws Exception {
        Integer id = 1;
        when(tableService.delete(id)).thenReturn(true);

        mockMvc.perform(delete("/tables/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteNotFoundTest() throws Exception {
        Integer id = 1;
        when(tableService.delete(id)).thenReturn(false);

        mockMvc.perform(delete("/tables/" + id))
                .andExpect(status().isNotFound());
    }

    private Table getTable() {
        return context.getBean("table", Table.class);
    }
}