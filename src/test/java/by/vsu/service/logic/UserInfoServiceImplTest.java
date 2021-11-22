package by.vsu.service.logic;

import by.vsu.dao.UserDao;
import by.vsu.dao.UserInfoDao;
import by.vsu.model.User;
import by.vsu.model.UserInfo;
import by.vsu.service.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserInfoServiceImplTest {

    @Mock
    private UserInfoDao userInfoDao;

    @InjectMocks
    private UserInfoServiceImpl userInfoService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getByIdTest() {
        UserInfo userInfo = getUserInfo();
        when(userInfoDao.findById(anyInt())).thenReturn(userInfo);

        UserInfo actual = userInfoService.getById(10);
        assertEquals(userInfo, actual);
    }

    @Test
    public void saveCreateNewObjectTest() {
        Integer id = 1;
        UserInfo userInfo = getUserInfo();
        userInfo.setId(null);

        doAnswer(invocation -> {
            UserInfo ui = invocation.getArgument(0);
            ui.setId(id);
            return null;
        }).when(userInfoDao).create(any(UserInfo.class));

        UserInfo actual = userInfoService.save(userInfo);
        userInfo.setId(id);
        assertEquals(userInfo, actual);
    }

    @Test
    public void saveUpdateTest() {
        Integer id = 1;
        UserInfo userInfo = getUserInfo();
        userInfo.setId(id);

        when(userInfoDao.findById(anyInt())).thenReturn(userInfo);
        userInfoService.save(userInfo);

        verify(userInfoDao, times(1)).update(userInfo);
    }

    @Test(expected = NotFoundException.class)
    public void saveUpdateThrowsExceptionTest() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);

        when(userInfoDao.findById(anyInt())).thenReturn(null);

        userInfoService.save(userInfo);
    }


    private UserInfo getUserInfo() {
        String name = "name";
        String email = "email@afs.ru";
        Date date = new Date();
        String phine = "+375 29 123 21 21";
        String avatar = "ava.pnp";
        User user = new User();
        user.setId(3);
        return new UserInfo(name, date, phine, email, avatar, user);
    }
}