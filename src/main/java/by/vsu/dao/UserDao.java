package by.vsu.dao;

import by.vsu.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//@Mapper
public interface UserDao {
//    @Select("select * from users")
    List<User> findAll();
}
