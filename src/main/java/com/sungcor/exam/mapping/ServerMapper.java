package com.sungcor.exam.mapping;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sungcor.exam.entity.Server;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ServerMapper extends BaseMapper<Server> {
    @Insert("INSERT INTO device_info_history(id,name,classCode,ip,value,create_time)" +
            " VALUES(#{id},#{name},#{classCode},#{ip},#{value},#{create_time})")
    int insertEntity(Server server);

    @Insert("REPLACE INTO device_info (id,NAME,ip,classCode,VALUE) VALUES(#{id},#{name},#{ip},#{classCode},#{value})")
    int updateEntity(Server server);

    @Insert("REPLACE INTO device_info_offlinecount (id,classCode,offLineCount) VALUES(#{id},#{classCode},#{offLineCount})")
    int updateServerOff(Server server);

    @Select("SELECT device_info.id,NAME,ip,classCode,VALUE,offLineCount\n" +
            "FROM device_info,device_info_offlinecount\n" +
            "WHERE device_info.`id`=device_info_offlinecount.`id`;")
    List<Server> serverListUnion();

    @Select("select * from device_info")
    List<Server> serverList();

    @Select("select * from device_info_offlinecount")
    List<Server> serverListOff();

    @Select("select * from device_info WHERE VALUE = 'on'")
    List<Server> getServerOnline();

    @Select("select * from device_info where name like #{arg0}")
    List<Server> getServerByName(String name);

    @Select("select * from device_info where ip like #{arg0}")
    List<Server> getServerByIp(String ip);

    @Delete("delete from device_info")
    int deleteTheTable();

}
