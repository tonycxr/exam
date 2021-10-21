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
        Date date = new Date();
        for(Server server:serverList){
            server.setCreate_time(date);
//            System.out.println(server);
            serverMapper.insertEntity(server);
        }
        return "插入数据成功"+date;
    }

    public String updateEntity() throws Exception{
        StringToClass stringToClass = new StringToClass();
        List<Server> serverList = stringToClass.getServer();
        for(Server server:serverList){
            serverMapper.updateEntity(server);
        }
        return "更新数据成功";
    }

    public String updateOffLine(){
        List<Server> servers = serverMapper.serverList();
        List<Server> serverList = serverMapper.serverListOff();
        for(Server server:servers){
            if(server.getValue().equals("off")){
                Server server2 = new Server(server.getId(),1);
                for(Server server1:serverList){
                    if(server1.getId().equals(server2.getId())){
                        server2.setOffLineCount(server1.getOffLineCount()+1);
                    }
                    serverMapper.updateServerOff(server2);
                    continue;
                }
                serverMapper.updateServerOff(server2);
            }
        }
        return "更新离线次数成功";
    }

    public Map<String,Integer> serverListOff(){
        List<Server> servers = serverMapper.serverListOff();
        Map<String,Integer> serverMap = new HashMap<>();
        for(Server server:servers){
            serverMap.put(server.getId(),server.getOffLineCount());
        }
        return serverMap;
    }

    public List<Server> serverList(){
        return serverMapper.serverList();
    }

    public Map<String, String> getServerStatus(){
        List<Server> servers = serverMapper.serverList();
        int PCServerOnline = 0;
        int PCServerOffline = 0;
        int PCServerUnknown = 0;
        int SwitchOnline = 0;
        int SwitchOffline = 0;
        int SwitchUnknown = 0;
        for(Server server:servers){
            switch (server.getClassCode()){
                case "PCServer":
                    switch (server.getValue()){
                        case "on":
                            PCServerOnline++;
                            break;
                        case "off":
                            PCServerOffline++;
                            break;
                        default:
                            PCServerUnknown++;
                            break;
                    }
                break;
                case "Switch":
                    switch (server.getValue()){
                        case "on":
                            SwitchOnline++;
                            break;
                        case "off":
                            SwitchOffline++;
                            break;
                        default:
                            SwitchUnknown++;
                            break;
                    }
                break;
            }
        }
        Map<String,String> serverStatusMap = new HashMap<>();
        serverStatusMap.put("PCServer在线情况","在线台数："+PCServerOnline
                +"，离线台数："+PCServerOffline+"，未知台数："+PCServerUnknown+"，合计："
                +(PCServerOnline+PCServerUnknown+PCServerOffline));
        serverStatusMap.put("Switch在线情况","在线台数："+SwitchOnline
                +"，离线台数："+SwitchOffline+"，未知台数："+SwitchUnknown+"，合计："
        +(SwitchOnline+SwitchUnknown+SwitchOffline));
        return serverStatusMap;
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
