package com.personal.seckill.dao;

import com.personal.seckill.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    @Select("select * from user u where u.id = #{id}")
    User getUserById(@Param("id")int id);

    @Insert("insert into user(id,name) values (#{id},#{name})")
    int addUser(User user);
}
