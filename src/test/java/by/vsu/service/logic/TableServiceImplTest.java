package by.vsu.service.logic;

import by.vsu.config.TestConfig;
import by.vsu.dao.TableDao;
import by.vsu.model.Table;
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
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TableServiceImplTest implements ApplicationContextAware {
    @Mock
    private TableDao tableDao;

    @InjectMocks
    private TableServiceImpl tableService;

    private ApplicationContext context;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Test
    public void saveNewObjectTest() {
        Integer id = 1;
        Table table = getTable();
        table.setId(null);

        doAnswer(i -> {
            Table t = i.getArgument(0);
            t.setId(id);
            return null;
        }).when(tableDao).create(table);

        Table actual = tableService.save(table);
        table.setId(id);
        assertEquals(table, actual);
    }

    @Test
    public void saveExistedObjectTest() {
        Integer id = 1;
        Table table = getTable();
        table.setId(id);

        when(tableDao.findById(id)).thenReturn(new Table());

        Table actual = tableService.save(table);
        assertEquals(table, actual);
    }

    @Test(expected = NotFoundException.class)
    public void saveNotExistedObjectTest() {
        Integer id = 1;
        Table table = getTable();
        table.setId(id);

        when(tableDao.findById(id)).thenReturn(null);

        tableService.save(table);
    }

    @Test
    public void deleteOneTest() {
        Integer id = 1;
        when(tableDao.delete(id)).thenReturn(1);

        assertTrue(tableService.delete(id));
    }

    @Test
    public void deleteZeroTest() {
        Integer id = 1;
        when(tableDao.delete(id)).thenReturn(0);

        assertFalse(tableService.delete(id));
    }

    private Table getTable() {
        return context.getBean("table", Table.class);
    }
}