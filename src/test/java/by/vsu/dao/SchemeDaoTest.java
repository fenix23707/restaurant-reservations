package by.vsu.dao;

import by.vsu.config.TestConfig;
import by.vsu.model.Comparison;
import by.vsu.model.Scheme;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class SchemeDaoTest implements ApplicationContextAware {
    private final static int SCHEME_SIZE = 6;

    @Autowired
    private SchemeDao schemeDao;

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Test
    public void findAllTest() {
        List<Scheme> schemes = schemeDao.findAll(SCHEME_SIZE, 0);

        assertEquals(SCHEME_SIZE, schemes.size());
        assertTrue(schemes.stream().allMatch(this::mainFieldsCorrect));
    }

    @Test
    public void findAllByWidthHeightTest() {
        int width = 100;
        Comparison compWidth = Comparison.EQ;
        int height = 200;
        Comparison compHeight = Comparison.GTE;
        List<Scheme> schemes = schemeDao.findAllByWidthHeight(width, height, compWidth, compHeight, SCHEME_SIZE, 0);

        assertEquals(3, schemes.size());
        assertTrue(schemes.stream().allMatch(scheme -> scheme.getWidth() == width && scheme.getHeight() >= 200));
    }

    @Test
    public void findAllBySquareTest() {
        int square = 10000;
        List<Scheme> schemes = schemeDao.findAllBySquare(square, Comparison.LTE, SCHEME_SIZE, 0);
        assertEquals(2, schemes.size());
        assertTrue(schemes.stream().allMatch(scheme -> scheme.getHeight() * scheme.getWidth() <= square));
    }

    @Test
    public void findByIdTest() {
        Integer id = 1;
        Scheme scheme = schemeDao.findById(id);

        assertTrue(mainFieldsCorrect(scheme));
        assertEquals(id, scheme.getId());
    }

    @Test
    public void findByRestaurantIdTest() {
        Integer restaurantId = 1;
        Scheme scheme = schemeDao.findByRestaurantId(restaurantId);

        assertTrue(mainFieldsCorrect(scheme));
        assertEquals(restaurantId, scheme.getRestaurant().getId());
    }

    @Test
    @Transactional
    public void createTest() {
        assertEquals(SCHEME_SIZE, schemeDao.findAll(SCHEME_SIZE, 0).size());
        Scheme scheme = getScheme();
        scheme.setId(null);
        schemeDao.create(scheme);

        int expectedSize = SCHEME_SIZE + 1;
        assertEquals(expectedSize, schemeDao.findAll(expectedSize, 0).size());
        assertNotNull(scheme.getId());
    }

    @Test
    @Transactional
    public void updateTest() {
        Integer id = 1;
        Scheme scheme = schemeDao.findById(id);
        Scheme newScheme = getScheme();
        scheme.setHeight(newScheme.getHeight());
        scheme.setWidth(newScheme.getWidth());
        scheme.setRestaurant(newScheme.getRestaurant());
        schemeDao.update(scheme);

        Scheme actual = schemeDao.findById(id);
        newScheme.setId(id);
        assertEquals(newScheme, actual);
    }

    @Test
    @Transactional
    public void deleteChangeSizeTest() {
        assertEquals(SCHEME_SIZE, schemeDao.findAll(SCHEME_SIZE, 0).size());

        schemeDao.delete(6);

        int expectedSize = SCHEME_SIZE - 1;
        assertEquals(expectedSize, schemeDao.findAll(expectedSize, 0).size());
    }

    private boolean mainFieldsCorrect(Scheme scheme) {
        return scheme.getHeight() != 0 &&
                scheme.getWidth() != 0 &&
                scheme.getRestaurant().getId() != null;
    }

    private Scheme getScheme() {
        return context.getBean("scheme", Scheme.class);
    }
}