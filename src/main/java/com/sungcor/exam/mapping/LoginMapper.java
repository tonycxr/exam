package com.sungcor.exam.mapping;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sungcor.exam.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Mapper
public interface LoginMapper extends BaseMapper<User> {
    @Select("Select * from User")
    List<User> getAllUser();

    @Insert("INSERT INTO USER(userName,password) VALUES(#{userName},#{password})")
    int insertUser(@RequestBody User user);

    @Delete("DELETE FROM USER WHERE userName = #{userName}")
    int deleteUser(String name);
}
