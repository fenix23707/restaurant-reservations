package by.vsu.service.logic;

import by.vsu.config.TestConfig;
import by.vsu.dao.SchemeDao;
import by.vsu.model.Scheme;
import by.vsu.service.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class SchemeServiceImplTest implements ApplicationContextAware {
    @Mock
    private SchemeDao schemeDao;

    @InjectMocks
    private SchemeServiceImpl schemeService;

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
        Scheme scheme = getScheme();
        scheme.setId(null);

        doAnswer(i -> {
            Scheme s = i.getArgument(0);
            s.setId(id);
            return null;
        }).when(schemeDao).create(any(Scheme.class));

        Scheme actual = schemeService.save(scheme);
        scheme.setId(id);
        assertEquals(scheme, actual);
    }

    @Test
    public void saveExistedObjectTest() {
        Integer id = 1;
        Scheme scheme = getScheme();
        scheme.setId(id);

        when(schemeDao.findById(id)).thenReturn(scheme);

        Scheme actual = schemeService.save(scheme);
        assertEquals(scheme, actual);
    }

    @Test(expected = NotFoundException.class)
    public void saveNotExistedObjectTest() {
        Integer id = 1;
        Scheme scheme = getScheme();
        scheme.setId(id);

        when(schemeDao.findById(id)).thenReturn(null);
        schemeService.save(scheme);
    }

    @Test
    public void deleteOneTest() {
        when(schemeDao.delete(anyInt())).thenReturn(1);
        assertTrue(schemeService.delete(1));
    }

    @Test
    public void deleteZeroTest() {
        when(schemeDao.delete(anyInt())).thenReturn(0);
        assertFalse(schemeService.delete(1));
    }

    private Scheme getScheme() {
        return context.getBean("scheme", Scheme.class);
    }
}