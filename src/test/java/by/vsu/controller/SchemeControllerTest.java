package by.vsu.controller;

import by.vsu.config.TestConfig;
import by.vsu.model.Comparison;
import by.vsu.model.Scheme;
import by.vsu.service.SchemeService;
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

@WebMvcTest(SchemeController.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Import(TestConfig.class)
public class SchemeControllerTest implements ApplicationContextAware {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SchemeService schemeService;

    @Autowired
    private List<Scheme> schemes;

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
        when(schemeService.getAll(anyInt(), anyInt())).thenReturn(schemes);

        mockMvc.perform(get("/schemes"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(schemes)));
    }

    @Test
    public void allByWidthHeightTest() throws Exception {
        int width = 100;
        int height = 100;
        Comparison compWidth = Comparison.GTE;
        Comparison compHeight = Comparison.EQ;

        List<Scheme> schemesByWidthHeight = schemes.stream()
                .filter(scheme -> scheme.getHeight() == height && scheme.getWidth() >= width)
                .collect(Collectors.toList());
        when(schemeService.getAllByWidthHeight(width, compWidth, height, compHeight, 10, 0))
                .thenReturn(schemesByWidthHeight);

        mockMvc.perform(get("/schemes/widthheight")
                .param("width", Integer.toString(width))
                .param("height", Integer.toString(height))
                .param("compWidth", compWidth.toString())
                .param("compHeight", compHeight.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(schemesByWidthHeight)));
    }

    @Test
    public void allBySquareTest() throws Exception {
        int square = 10000;
        Comparison comparison = Comparison.GTE;

        List<Scheme> schemesBySquare = schemes.stream()
                .filter(scheme -> scheme.getHeight() * scheme.getWidth() >= square)
                .collect(Collectors.toList());
        when(schemeService.getAllBySquare(square, comparison, 10, 0))
                .thenReturn(schemesBySquare);

        mockMvc.perform(get("/schemes/square")
                .param("square", Integer.toString(square))
                .param("comparison", comparison.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(schemesBySquare)));
    }

    @Test
    public void getByIdTest() throws Exception {
        Integer id = 1;
        Scheme scheme = getScheme();
        scheme.setId(id);
        when(schemeService.getById(id)).thenReturn(scheme);

        mockMvc.perform(get("/schemes/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(scheme)));
    }

    @Test
    public void newSchemeCreateTest() throws Exception {
        Integer id = 1;
        Scheme response = getScheme();
        response.setId(id);
        Scheme scheme = getScheme();
        scheme.setId(null);

        doAnswer(i -> {
            Scheme s = i.getArgument(0);
            s.setId(id);
            return s;
        }).when(schemeService).save(any(Scheme.class));

        mockMvc.perform(post("/schemes")
                .content(mapper.writeValueAsString(scheme))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @Test
    public void newSchemeConflictTest() throws Exception {
        Scheme scheme = getScheme();
        scheme.setId(1);
        mockMvc.perform(post("/schemes")
                .content(mapper.writeValueAsString(scheme))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void updateSchemeOkTest() throws Exception {
        Integer id = 1;
        Scheme scheme = getScheme();
        scheme.setId(id);

        when(schemeService.save(any(Scheme.class))).thenReturn(scheme);

        mockMvc.perform(put("/schemes")
                .content(mapper.writeValueAsString(scheme))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(scheme)));
    }

    @Test
    public void updateSchemeBadRequestTest() throws Exception {
        Scheme scheme = getScheme();
        scheme.setId(null);

        mockMvc.perform(put("/schemes")
                .content(mapper.writeValueAsString(scheme))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteNoContentTest() throws Exception {
        Integer id = 1;
        when(schemeService.delete(id)).thenReturn(true);

        mockMvc.perform(delete("/schemes/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteNotFoundTest() throws Exception {
        Integer id = 1;
        when(schemeService.delete(id)).thenReturn(false);

        mockMvc.perform(delete("/schemes/" + id))
                .andExpect(status().isNotFound());
    }

    private Scheme getScheme() {
        return context.getBean("scheme", Scheme.class);
    }
}