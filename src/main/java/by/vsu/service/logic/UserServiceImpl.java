package by.vsu.service.logic;

import by.vsu.dao.UserDao;
import by.vsu.model.User;
import by.vsu.service.UserService;
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
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void save(User user) {
        if (user.getId() != null) {
            userDao.update(user);
        } else {
            userDao.create(user);
        }
    }
}
