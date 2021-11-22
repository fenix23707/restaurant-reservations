package by.vsu.service;

import by.vsu.model.UserInfo;

public interface UserInfoService {
    /**
     * Gets userInfo by its id.
     * @param id UserInfo id.
     * @return UserInfo.
     */
    UserInfo getById(Integer id);

    /**
     * Create new userInfo if user doesn't have id. Or update old userInfo if it have id.
     * @param userInfo UserInfo data for save.
     * @return Saved userInfo object.
     */
    UserInfo save(UserInfo userInfo);
}
