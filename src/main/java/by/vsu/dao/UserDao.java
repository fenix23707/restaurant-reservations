package by.vsu.dao;

import by.vsu.model.User;

import java.util.List;

public interface UserDao {
    /**
     * Find list of users.
     * @param limit number of users to return.
     * @param skip number of users to skip.
     * @return list of users.
     */
    List<User> findAll(int limit, int skip);

    /**
     * Find user by its id.
     * @return User.
     */
    User findById(Integer id);

    /**
     * Create new user.User id should be auto-generated.
     * Created User object saved in param user.
     * @param user User data.
     */
    void create(User user);

    /**
     * Updates user using given data.
     * @param user User data for update. Should have id set
     */
    void update(User user);

    /**
     * Deletes user by its id.
     * @param id User id.
     * @return number of deleted users.
     */
    int delete(Integer id);
}
