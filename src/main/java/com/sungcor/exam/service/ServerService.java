package com.sungcor.exam.service;

import com.alibaba.fastjson.JSONObject;
import com.sungcor.exam.entity.Datalist;
import com.sungcor.exam.entity.Getdata;
import com.sungcor.exam.entity.Postdata;
import com.sungcor.exam.entity.Server;
import com.sungcor.exam.mapping.ServerMapper;
import com.sungcor.exam.utils.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ServerService {
    @Autowired
    private ServerMapper serverMapper;

    @Value("${constants.url.findServer}")
    private String theURLToFindServer;

    @Resource
    private HttpUtil httpUtil;

    public String getServer(Postdata postdata){
        //post请求
        HttpMethod method = HttpMethod.POST;
        String s = JSONObject.toJSONString(postdata);
        JSONObject json = JSONObject.parseObject(s);
//        System.out.println("发送数据：" + json.toString());
        String res = httpUtil.HttpRestClient(theURLToFindServer, method, json);
        Getdata getdata = httpUtil.JsonToInterChanger(res);
        Datalist[] DataLists = getdata.getDataList();
        List<Server> servers = new ArrayList<>();
        for(Datalist datalist: DataLists){
            String url = "http://192.168.0.28/network/v2/openapi/datasets/states/" +
                    "object_available/query?apikey=9cc4871e46094635a19d26557f9bb7f4&object_ids="+datalist.getId()+"&state=&object_type=object.available";
            Server server = new Server(url);
            server.setId(datalist.getId());
            server.setName(datalist.getName());
            server.setIp(datalist.getIp());
            server.setClassCode(datalist.getClassCode());
            servers.add(server);
        }
        for(Server server: servers){
            String res2 = httpUtil.HttpRestClient(server.getUrl(),HttpMethod.GET,json);
//            System.out.println(res2);
            Getdata getData2 = httpUtil.JsonToInterChanger(StringUtils.strip(res2,"[]"));
            if(getData2!=null){
                server.setValue(getData2.getValue());
            }
            else{
                server.setValue("unknown");
            }
        }
        Date date = new Date();
        for(Server server:servers){
            server.setCreate_time(date);
//            System.out.println(server);
            serverMapper.insertEntity(server);
            serverMapper.updateEntity(server);
        }
        return "插入并更新数据成功";
    }


    public Map<String,Object> getStatus2() {
        Map<String,Object> list1=serverMapper.pcServerStatus();
        Map<String,Object> list2=serverMapper.switchStatus();
        Map<String,Object> target=new HashMap<>();
        Map<String,Integer> pcServer = new HashMap<>();
        Map<String,Integer> Switch = new HashMap<>();
        pcServer.put("online",Integer.parseInt(list1.get("PConline").toString()));
        pcServer.put("offline",Integer.parseInt(list1.get("PCoffline").toString()));
        pcServer.put("unknown",Integer.parseInt(list1.get("PCunknown").toString()));
        pcServer.put("total",Integer.parseInt(list1.get("PCtotal").toString()));
        target.put("PCServer",pcServer);
        Switch.put("online",Integer.parseInt(list2.get("Swonline").toString()));
        Switch.put("offline",Integer.parseInt(list2.get("Swoffline").toString()));
        Switch.put("unknown",Integer.parseInt(list2.get("Swunknown").toString()));
        Switch.put("total",Integer.parseInt(list2.get("Swtotal").toString()));
        target.put("Switch",Switch);
        return target;
    }

    public boolean updateOffLine(){
        List<Server> servers = serverMapper.serverList();
        List<Server> serverList = serverMapper.serverListOff();
        for(Server server:servers){
            if(server.getValue().equals("off")){
                Server server2 = new Server(server.getId(),server.getClassCode(),1);
                for(Server server1:serverList){
                    if(server1.getId().equals(server2.getId())){
                        server2.setOffLineCount(server1.getOffLineCount()+1);
                    }
                    serverMapper.updateServerOff(server2);
                }
                serverMapper.updateServerOff(server2);
            }
        }
        return true;
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

//    public Map<String, Integer> getServerStatus(){
//        List<Server> servers = serverMapper.serverList();
//        int PCServerOnline = 0;
//        int PCServerOffline = 0;
//        int PCServerUnknown = 0;
//        int SwitchOnline = 0;
//        int SwitchOffline = 0;
//        int SwitchUnknown = 0;
//        int total = 0;
//        for(Server server:servers){
//            total++;
//            switch (server.getClassCode()){
//                case "PCServer":
//                    switch (server.getValue()){
//                        case "on":
//                            PCServerOnline++;
//                            break;
//                        case "off":
//                            PCServerOffline++;
//                            break;
//                        default:
//                            PCServerUnknown++;
//                            break;
//                    }
//                break;
//                case "Switch":
//                    switch (server.getValue()){
//                        case "on":
//                            SwitchOnline++;
//                            break;
//                        case "off":
//                            SwitchOffline++;
//                            break;
//                        default:
//                            SwitchUnknown++;
//                            break;
//                    }
//                break;
//            }
//        }
//        Map<String,Integer> serverStatusMap = new LinkedHashMap<>();
//        serverStatusMap.put("PCServerOnline",PCServerOnline);
//        serverStatusMap.put("PCServerOffline",PCServerOffline);
//        serverStatusMap.put("PCServerUnknown",PCServerUnknown);
//        serverStatusMap.put("SwitchOnline",SwitchOnline);
//        serverStatusMap.put("SwitchOffline",SwitchOffline);
//        serverStatusMap.put("SwitchUnknown",SwitchUnknown);
//        serverStatusMap.put("total",total);
//        return serverStatusMap;
//    }

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
