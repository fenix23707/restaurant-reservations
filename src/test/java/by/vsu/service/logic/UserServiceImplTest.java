package by.vsu.service.logic;

import by.vsu.dao.UserDao;
import by.vsu.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    private List<User> users;

    public UserServiceImplTest() {
        this.users = Arrays.asList(
                new User(1, "login1", "password1", User.Role.ADMIN, User.Status.ACTIVE),
                new User(2, "login2", "password2", User.Role.MANAGER, User.Status.INACTIVE),

                new User(3, "login3", "password3", User.Role.VISITOR, User.Status.ACTIVE),
                new User(4, "login4", "password4", User.Role.VISITOR, User.Status.ACTIVE),

                new User(5, "login5", "password5", User.Role.VISITOR, User.Status.ACTIVE),
                new User(6, "login6", "password6", User.Role.ADMIN, User.Status.ACTIVE),

                new User(7, "login7", "password7", User.Role.VISITOR, User.Status.ACTIVE),
                new User(8, "login8", "password8", User.Role.MANAGER, User.Status.ACTIVE),
                new User(9, "login9", "password9", User.Role.MANAGER, User.Status.ACTIVE),
                new User(10, "login10", "password10", User.Role.MANAGER, User.Status.ACTIVE)
        );
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllTest() {
        when(userDao.findAll(users.size(), 0)).thenReturn(users);
        List<User> actual = userService.getUsers(users.size(), 0);
        assertEquals(users, actual);
    }

    @Test
    public void getAllPageSizePageNumTest() {
        int pageSize = 2;
        int pageNum = 2;
        List<User> subUsers = users.subList(4, 6);
        when(userDao.findAll(pageSize, pageNum * pageNum)).thenReturn(subUsers);

        List<User> actual = userService.getUsers(pageSize, pageNum);
        assertEquals(subUsers, actual);
    }


    @Test
    public void getUserByIdTest() {
        Integer id = 1;
        when(userDao.findById(id)).thenReturn(users.get(0));

        User user = userService.getUserById(id);
        assertEquals(users.get(0), user);
    }

    @Test
    public void saveNewObjectTest() {
        User user = new User("newL", "newP", User.Role.ADMIN, User.Status.ACTIVE);
        Integer id = 11;

        doAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(id);
            return null;
        }).when(userDao).create(user);

        User u = userService.save(user);

        assertEquals(id, u.getId());
    }

    @Test
    public void deleteSuccessfulTest() {
        when(userDao.delete(anyInt())).thenReturn(1);
        boolean result = userService.delete(10);
        assertTrue(result);
    }

    @Test
    public void deleteZeroTest() {
        when(userDao.delete(anyInt())).thenReturn(0);
        boolean result = userService.delete(10);
        assertFalse(result);
    }
}