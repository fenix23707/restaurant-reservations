package by.vsu.service.logic;

import by.vsu.dao.UserInfoDao;
import by.vsu.model.UserInfo;
import by.vsu.service.UserInfoService;
import by.vsu.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private UserInfoDao userInfoDao;

    @Autowired
    public void setUserInfoDao(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    @Override
    public UserInfo getById(Integer id) {
        return userInfoDao.findById(id);
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        if (userInfo.getId() != null) {
            UserInfo temp = userInfoDao.findById(userInfo.getId());
            if (temp != null) {
                userInfoDao.update(userInfo);
            } else {
                throw new NotFoundException("there is not user info with id = " + userInfo.getId());
            }
        } else {
            userInfoDao.create(userInfo);
        }
        return userInfo;
    }
}
