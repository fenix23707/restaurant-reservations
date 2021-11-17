package by.vsu.service.logic;

import by.vsu.dao.UserDao;
import by.vsu.model.User;
import by.vsu.service.UserService;
import by.vsu.service.exception.UserNotFoundException;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getUsers(int pageSize, int pageNum) {
        return userDao.findAll(pageSize, pageNum * pageSize);
    }

    @Override
    public User getUserById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public User save(User user) {
        if (user.getId() != null) {
            User temp = userDao.findById(user.getId());
            if (temp == null) {
                throw new UserNotFoundException("there is not user with id = " + user.getId());
            }
            userDao.update(user);
        } else {
            userDao.create(user);
        }
        return user;
    }

    @Override
    public boolean delete(Integer id) {
        return userDao.delete(id) != 0;
    }
}
