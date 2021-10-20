package com.sungcor.exam.service;

import com.sungcor.exam.entity.Server;
import com.sungcor.exam.mapping.ServerMapper;
import com.sungcor.exam.utils.StringToClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return "更新成功";
    }

    public List<Server> serverList(){
        return serverMapper.serverList();
    }

    public Map<String, Integer> getServerOnline(){
        List<Server> serverOnline = serverMapper.getServerOnline();
        int PCServer = 0;
        int Switch = 0;
        for(Server server:serverOnline){
            switch (server.getClassCode()){
                case "PCServer":
                    PCServer +=1;
                    break;
                case "Switch":
                    Switch +=1;
                    break;
            }
        }
        Map<String, Integer> onLineServers = new HashMap<>();
        onLineServers.put("PCServer",PCServer);
        onLineServers.put("Switch",Switch);
        return onLineServers;
    }

    public List<Server> getServerByName(String name){
        String name2 = "%"+name+"%";
        return serverMapper.getServerByName(name2);
    }

    public List<Server> getServerByIp(String ip){
        String ip2 = "%"+ip+"%";
        return serverMapper.getServerByIp(ip2);
    }

    public String deleteAll(){
        serverMapper.deleteTheTable();
        return "删除成功";
    }

}
