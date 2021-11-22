package by.vsu.controller;

import by.vsu.model.User;
import by.vsu.service.UserService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    private List<User> users;

    private User user;

    public UserControllerTest() {
        this.users = new ArrayList<>();
        users.add(new User(1, "l1", "p1", User.Role.ADMIN, User.Status.ACTIVE));
        users.add(new User(2, "l2", "p2", User.Role.MANAGER, User.Status.ACTIVE));

        this.user = new User(1, "l", "p", User.Role.ADMIN, User.Status.INACTIVE);
    }

    @Test
    public void usersTest() throws Exception {

        when(userService.getUsers(10, 0)).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(users.get(0).getId()))
                .andExpect(jsonPath("$[0].login").value(users.get(0).getLogin()))
                .andExpect(jsonPath("$[0].password").value(users.get(0).getPassword()))
                .andExpect(jsonPath("$[0].role").value(users.get(0).getRole().toString()))
                .andExpect(jsonPath("$[0].status").value(users.get(0).getStatus().toString()))
                .andExpect(jsonPath("$[1].login").value(users.get(1).getLogin()));
    }

    @Test
    public void findUserTest() throws Exception {
        when(userService.getUserById(anyInt())).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.login").value(user.getLogin()))
                .andExpect(jsonPath("$.password").value(user.getPassword()))
                .andExpect(jsonPath("$.role").value(user.getRole().toString()))
                .andExpect(jsonPath("$.status").value(user.getStatus().toString()));
    }

    @Test
    public void createTest() throws Exception {
        when(userService.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                .content(new ObjectMapper().writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    public void updateTest() throws Exception {
        when(userService.save(any(User.class))).thenReturn(user);

        mockMvc.perform(put("/users")
                .content(new ObjectMapper().writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    public void updateUserNotFoundTest() throws Exception {
        when(userService.save(any(User.class))).thenThrow(NotFoundException.class);

        mockMvc.perform(put("/users")
                .content(new ObjectMapper().writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteTest() throws Exception {
        when(userService.delete(anyInt())).thenReturn(true);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteZeroTest() throws Exception {
        when(userService.delete(anyInt())).thenReturn(false);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNotFound());
    }
}