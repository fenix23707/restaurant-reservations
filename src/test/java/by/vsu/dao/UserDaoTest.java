package by.vsu.dao;

import by.vsu.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:beans.xml"})
public class UserDaoTest {
    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Test
    public void findAllCountTest() {
        List<User> users = userDao.findAll();
        assertEquals(10, users.size());
    }

    @Test
    public void findAllNotNullValuesTest() {
        List<User> users = userDao.findAll();
        assertTrue(users.stream().allMatch(user -> user != null));
    }


}