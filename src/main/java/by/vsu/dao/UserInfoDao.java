package by.vsu.dao;

import by.vsu.model.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserInfoDao {
    /**
     * Find list of userInfo.
     * @param limit number of userInfo to return.
     * @param skip number of userInfo to skip.
     * @return list of userInfo.
     */
    @Select("SELECT id, name, birthday, phone, avatar, email, user_id " +
            "FROM user_info " +
            "LIMIT #{limit} OFFSET #{skip} ")
    @ResultMap("userInfoResultMap")
    List<UserInfo> findAll(@Param("limit") int limit, @Param("skip") int skip);

    /**
     * Find userInfo by its id.
     * @param id UserInfo id.
     * @return UserInfo.
     */
    @Select("SELECT id, name, birthday, phone, avatar, email, user_id " +
            "FROM user_info " +
            "WHERE id = #{id}")
    @ResultMap("userInfoResultMap")
    UserInfo findById(@Param("id") Integer id);

    /**
     * Create new userInfo.UserInfo id should be auto-generated.
     * Created UserInfo object saved in param userInfo.
     * @param userInfo UserInfo data.
     */
    @Insert("INSERT INTO user_info (name, birthday, phone, avatar, email, user_id) " +
            "VALUES(#{name}, #{birthday}, #{phone}, #{avatar}, #{email}, #{user.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void create(UserInfo userInfo);

    /**
     * Updates userInfo using given data.
     * @param userInfo UserInfo data for update. Should have id set.
     */
    @Update("UPDATE user_info " +
            "SET name = #{name}, birthday = #{birthday}, phone = #{phone}, avatar = #{avatar}, email = #{email}, user_id = #{user.id} " +
            "WHERE id = #{id}")
    void update(UserInfo userInfo);
}
