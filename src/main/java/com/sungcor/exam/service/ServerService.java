package com.sungcor.exam.service;

import com.sungcor.exam.entity.Server;
import com.sungcor.exam.mapping.ServerMapper;
import com.sungcor.exam.utils.StringToClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerService {
    @Autowired(required = false)
    private ServerMapper serverMapper;

    public int insertEntity() throws Exception {
        StringToClass stringToClass = new StringToClass();
        List<Server> serverList = stringToClass.getServer();
        for(Server server:serverList){
//            System.out.println(server);
            serverMapper.insertEntity(server);
        }
        return 1;
    }
}
