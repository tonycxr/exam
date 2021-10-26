package com.sungcor.exam.mapping;

import com.sungcor.exam.entity.Server;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ServerMapper {
    @Insert("INSERT INTO device_info_history(id,name,classCode,ip,value,create_time)" +
            " VALUES(#{id},#{name},#{classCode},#{ip},#{value},#{create_time})")
    void insertEntity(Server server);

    @Insert("REPLACE INTO device_info (id,NAME,ip,classCode,VALUE) VALUES(#{id},#{name},#{ip},#{classCode},#{value})")
    void updateEntity(Server server);

    @Insert("REPLACE INTO device_info_offlinecount (id,classCode,offLineCount) VALUES(#{id},#{classCode},#{offLineCount})")
    void updateServerOff(Server server);

    @Select("SELECT classcode,\n" +
            "SUM(CASE WHEN VALUE = 'off' AND classCode = 'PCServer' THEN 1 END) AS 'PCoffline',\n" +
            "SUM(CASE WHEN VALUE = 'on' AND classCode = 'PCServer' THEN 1 END) AS 'PConline',\n" +
            "SUM(CASE WHEN VALUE = 'unknown' AND classCode = 'PCServer' THEN 1 END) AS 'PCunknown',\n" +
            "SUM(CASE WHEN classCode = 'PCServer' THEN 1 END) AS 'PCtotal'\n" +
            "FROM device_info")
    Map<String,Object> pcServerStatus();

    @Select("SELECT classcode,\n" +
            "SUM(CASE WHEN VALUE = 'off' AND classCode = 'Switch' THEN 1 END) AS 'Swoffline',\n" +
            "SUM(CASE WHEN VALUE = 'on' AND classCode = 'Switch' THEN 1 END) AS 'Swonline',\n" +
            "SUM(CASE WHEN VALUE = 'unknown' AND classCode = 'Switch' THEN 1 END) AS 'Swunknown',\n" +
            "SUM(CASE WHEN classCode = 'Switch' THEN 1 END) AS 'Swtotal'\n" +
            "FROM device_info;")
    Map<String,Object> switchStatus();

    @Select("select * from device_info")
    List<Server> serverList();

    @Select("select * from device_info_offlinecount")
    List<Server> serverListOff();

    @Select("select * from device_info where name like #{arg0}")
    List<Server> getServerByName(String name);

    @Select("select * from device_info where ip like #{arg0}")
    List<Server> getServerByIp(String ip);

    @Delete("delete from device_info")
    void deleteTheTable();

}
