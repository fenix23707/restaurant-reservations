package by.vsu.dao;

import by.vsu.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDao {
    List<User> findAll();

    User findById(Integer id);

    void create(User user);

    void update(User user);
}
