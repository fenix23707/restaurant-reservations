package by.vsu.service;

import by.vsu.model.User;

import java.util.List;

public interface UserService {
    /**
     * Get list of users.
     * In case nothing was found, empty list is returned.
     * @param pageSize Pagination param. Number of users to return on a page.
     * @param pageNum Pagination param. Number of the page to return. Starts from 0.
     * @return List of users.
     */
    List<User> getUsers(int pageSize, int pageNum);

    /**
     * Gets user by its id.
     * @param id User id.
     * @return User.
     */
    User getUserById(Integer id);

    /**
     * Create new user if user doesn't have id. Or update old user if it have id.
     * @param user User data for save.
     * @return Saved user object.
     */
    User save(User user);

    /**
     * Delete user by its id.
     * @param id User id.
     * @return Flag that shows whether user has been deleted.
     */
    boolean delete(Integer id);
}
