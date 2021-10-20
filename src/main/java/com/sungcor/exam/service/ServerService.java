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
        return "初始数据插入成功";
    }

    public String updateEntity() throws Exception{
        StringToClass stringToClass = new StringToClass();
        List<Server> serverList1 = stringToClass.getServer();
        List<Server> serverList2 = serverMapper.serverList();
//        for(Server server:serverList1){
//            if(server.getId().equals("5d4a43ac87aa13ea48a938b9")){
//                server.setValue("off");
//            }
//        }
        for(Server server1:serverList1){
            for(Server server2:serverList2){
                if(server1.getId().equals(server2.getId())){
                    if(server2.getValue().equals("on") && server1.getValue().equals("off")){
                        server1.setOffLineCount(server2.getOffLineCount()+1);
                    }else{
                        server1.setOffLineCount(server2.getOffLineCount());
                    }
                }
            }
            serverMapper.updateEntity(server1);
        }
        return "更新数据成功";
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
