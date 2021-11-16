package by.vsu.service.logic;

import by.vsu.dao.UserDao;
import by.vsu.model.User;
import by.vsu.model.UserRole;
import by.vsu.model.UserStatus;
import by.vsu.service.UserService;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    private List<User> users;

    public UserServiceImplTest() {
        this.users = Arrays.asList(
                new User(1, "login1", "password1", UserRole.ADMIN, UserStatus.ACTIVE),
                new User(2, "login2", "password2", UserRole.MANAGER, UserStatus.INACTIVE),
                new User(3, "login3", "password3", UserRole.VISITOR, UserStatus.ACTIVE)
        );
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllTest() {
        when(userDao.findAll()).thenReturn(users);

        List<User> actual = userService.findAll();
        assertEquals(users, actual);
    }
}