package com.sungcor.exam.mapping;

import com.sungcor.exam.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LoginMapper {
    @Select("Select * from User")
    List<User> getAllUser();
}
