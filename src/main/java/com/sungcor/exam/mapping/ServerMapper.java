package com.sungcor.exam.mapping;

import com.sungcor.exam.entity.Server;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ServerMapper{
    @Insert("INSERT INTO device_info(id,name,classCode,ip,value) VALUES(#{id},#{name},#{classCode},#{ip},#{value})")
    public int insertEntity(Server server);

    @Select("select * from device_info")
    public List<Server> serverList();

    @Select("select * from device_info WHERE VALUE = 'on'")
    public List<Server> getServerOnline();

}
