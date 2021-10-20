package com.sungcor.exam.mapping;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sungcor.exam.entity.Server;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ServerMapper extends BaseMapper<Server> {
    @Insert("INSERT INTO device_info(id,name,classCode,ip,value,offLineCount)" +
            " VALUES(#{id},#{name},#{classCode},#{ip},#{value},#{offLineCount})")
    int insertEntity(Server server);

    @Update("UPDATE device_info SET Name=#{name},classCode=#{classCode},ip=#{ip}," +
            "value=#{value},offLineCount=#{offLineCount} where id=#{id}")
    int updateEntity(Server server);

    @Select("select * from device_info")
    List<Server> serverList();

    @Select("select * from device_info WHERE VALUE = 'on'")
    List<Server> getServerOnline();

    @Select("select * from device_info where name like #{arg0}")
    List<Server> getServerByName(String name);

    @Select("select * from device_info where ip like #{arg0}")
    List<Server> getServerByIp(String ip);

    @Delete("delete from device_info")
    int deleteTheTable();

}
