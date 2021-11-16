package by.vsu.dao;

import by.vsu.model.User;
import by.vsu.model.UserRole;
import by.vsu.model.UserStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    public void findByIdNotNullTest() {
        User user = userDao.findById(1);
        assertNotNull(user);
    }

    @Test
    public void findByIdFieldsNotNullTest() {
        User user = userDao.findById(1);
        assertNotNull(user.getId());
        assertNotNull(user.getLogin());
        assertNotNull(user.getPassword());
        assertNotNull(user.getRole());
        assertNotNull(user.getStatus());
    }

    @Test
    public void findByIdSameIdTest() {
        Integer id = 1;
        User user = userDao.findById(id);
        assertEquals(id, user.getId());
    }

    @Test
    @Transactional
    public void createKeyGeneratesTest() {
        User user = new User("login", "password", UserRole.ADMIN, UserStatus.ACTIVE);
        userDao.create(user);
        assertNotNull(user.getId());
    }

    @Test
    @Transactional
    public void createChangeCountTest() {
        int startSize = userDao.findAll().size();
        User user = new User("login", "password", UserRole.ADMIN, UserStatus.ACTIVE);
        userDao.create(user);
        int endSize = userDao.findAll().size();
        assertEquals(startSize + 1, endSize);
    }


    @Test
    @Transactional
    public void updateFieldsChangeTest() {
        Integer id = 1;
        String newLogin = "l";
        String newPassword = "p";
        UserRole newRole = UserRole.ADMIN;
        UserStatus newStatus = UserStatus.INACTIVE;
        User user = new User(id, newLogin, newPassword, newRole, newStatus);

        userDao.update(user);
        User newUser = userDao.findById(id);

        assertEquals(id, newUser.getId());
        assertEquals(newLogin, newUser.getLogin());
        assertEquals(newPassword, newUser.getPassword());
        assertEquals(newRole, newUser.getRole());
        assertEquals(newStatus, newUser.getStatus());
    }
}