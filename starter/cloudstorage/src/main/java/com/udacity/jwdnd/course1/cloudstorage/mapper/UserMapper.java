package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;
@Mapper
public interface UserMapper {
    final String SELECT_BY_NAME = "SELECT * FROM USERS WHERE username = #{username}";
    final String SELECT_BY_ID = "SELECT * FROM USERS WHERE userid = #{userId}";
    final String INSERT = "INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES (#{username}, #{salt}, #{password}, #{firstName}, #{lastName})";
    final String UPDATE = "UPDATE USERS SET username = #{username}, " +
            "password = #{password}, " +
            "firstname = #{firstName}, " +
            "lastname = #{lastName} WHERE userid = #{userId}";
    final String DELETE = "DELETE FROM USERS WHERE userid = #{userId}";

    @Select(SELECT_BY_ID)
    User getUserById(@Param("userId") Integer userId);

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUserByName(String username);

    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES (#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(User user);

    @Update("UPDATE USERS SET username = #{username}, " +
            "password = #{password}, " +
            "firstname = #{firstName}, " +
            "lastname = #{lastName} WHERE userid = #{userId}")
    void updateUser(User user);
    void updatePassword(@Param("password") String password, @Param("userId") Integer userId);

    @Delete("DELETE FROM USERS WHERE userid = #{userId}")
    void deleteUser(Integer userId);

}
