package com.sungcor.exam.mapping;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sungcor.exam.entity.Server;
import com.sungcor.exam.entity.dataList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface ServerMapper{
    @Insert("INSERT INTO device_info(id,name,classCode,ip,value) VALUES(#{id},#{name},#{classCode},#{ip},#{value})")
    public int insertEntity(Server server);

}
