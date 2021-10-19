package com.sungcor.exam.service;

import com.sungcor.exam.entity.Server;
import com.sungcor.exam.mapping.ServerMapper;
import com.sungcor.exam.utils.StringToClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import static com.sungcor.exam.utils.TimeUtil.changeFormat;

@Service
public class ServerService {
    @Autowired(required = false)
    private ServerMapper serverMapper;

    public String insertEntity() throws Exception {
        StringToClass stringToClass = new StringToClass();
        List<Server> serverList = stringToClass.getServer();
        for(Server server:serverList){
//            System.out.println(server);
            serverMapper.insertEntity(server);
        }
        return "上传成功";
    }

    public List<Server> serverList(){
        return serverMapper.serverList();
    }
}
