package by.vsu.controller;

import by.vsu.model.User;
import by.vsu.model.UserInfo;
import by.vsu.service.UserInfoService;
import by.vsu.service.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserInfoController.class)
public class UserInfoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserInfoService userInfoService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getByIdTest() throws Exception {
        UserInfo userInfo = getUserInfo();
        userInfo.setId(1);
        when(userInfoService.getById(anyInt())).thenReturn(userInfo);

        mockMvc.perform(get("/userinfo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userInfo.getId()))
                .andExpect(jsonPath("$.name").value(userInfo.getName()))
                .andExpect(jsonPath("$.avatar").value(userInfo.getAvatar()))
                .andExpect(jsonPath("$.phone").value(userInfo.getPhone()))
                .andExpect(jsonPath("$.email").value(userInfo.getEmail()))
                .andExpect(jsonPath("$.user.id").value(userInfo.getUser().getId()));
    }

    @Test
    public void createTest() throws Exception {
        UserInfo userInfo = getUserInfo();
        when(userInfoService.save(any(UserInfo.class))).thenReturn(userInfo);

        mockMvc.perform(post("/userinfo")
                .content(new ObjectMapper().writeValueAsString(userInfo))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    public void updateTest() throws Exception {
        UserInfo userInfo = getUserInfo();
        when(userInfoService.save(any(UserInfo.class))).thenReturn(userInfo);

        mockMvc.perform(put("/userinfo")
                .content(new ObjectMapper().writeValueAsString(userInfo))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    public void updateNotFoundTest() throws Exception {
        UserInfo userInfo = getUserInfo();
        when(userInfoService.save(any(UserInfo.class))).thenThrow(NotFoundException.class);

        mockMvc.perform(put("/userinfo")
                .content(new ObjectMapper().writeValueAsString(userInfo))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private UserInfo getUserInfo() {
        String name = "name";
        String email = "email@afs.ru";
        Date date = new Date();
        String phine = "+375 29 123 21 21";
        String avatar = "ava.pnp";
        User user = new User();
        user.setId(3);
        return new UserInfo(1, name, date, phine, email, avatar, user);
    }
}