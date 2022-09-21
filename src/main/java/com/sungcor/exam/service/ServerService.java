package com.sungcor.exam.service;

import com.alibaba.fastjson.JSONObject;
import com.sungcor.exam.entity.*;
import com.sungcor.exam.mapping.ServerMapper;
import com.sungcor.exam.utils.HttpUtil;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ServerService {
    private final ServerMapper serverMapper;

    @Value("${constants.url.findServer}")
    private String theURLToFindServer;

    @Resource
    private HttpUtil httpUtil;

    public ServerService(ServerMapper serverMapper) {
        this.serverMapper = serverMapper;
    }

    public String getServer(Postdata postdata){
        HttpMethod method = HttpMethod.POST;
        String s = JSONObject.toJSONString(postdata);
        JSONObject json = JSONObject.parseObject(s);
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
            serverMapper.insertEntity(server);
            serverMapper.updateEntity(server);
        }
        return "插入并更新数据成功";
    }


//    public Map<String,Object> getStatus() {
//        Map<String,Object> list1=serverMapper.pcServerStatus();
//        Map<String,Object> list2=serverMapper.switchStatus();
//        Map<String,Object> target=new HashMap<>();
//        Map<String,Integer> pcServer = new HashMap<>();
//        Map<String,Integer> Switch = new HashMap<>();
//        pcServer.put("online",Integer.parseInt(list1.get("PConline").toString()));
//        pcServer.put("offline",Integer.parseInt(list1.get("PCoffline").toString()));
//        pcServer.put("unknown",Integer.parseInt(list1.get("PCunknown").toString()));
//        pcServer.put("total",Integer.parseInt(list1.get("PCtotal").toString()));
//        target.put("PCServer",pcServer);
//        Switch.put("online",Integer.parseInt(list2.get("Swonline").toString()));
//        Switch.put("offline",Integer.parseInt(list2.get("Swoffline").toString()));
//        Switch.put("unknown",Integer.parseInt(list2.get("Swunknown").toString()));
//        Switch.put("total",Integer.parseInt(list2.get("Swtotal").toString()));
//        target.put("Switch",Switch);
//        return target;
//    }

    public Map<String,Integer> listMaptoMap(List<Map<String,Integer>> listMap){
        Map<String,Integer> serverMap = new HashMap<>();
        for(Map map:listMap){
            serverMap.put(map.get("id").toString(),(Integer) map.get("offLineCount"));
        }
        return serverMap;
    }

    public boolean updateOffLine(){
        List<Server> servers = serverMapper.serverList();
        List<Map<String,Integer>> listMap = serverMapper.serverMapOff();
        Map<String,Integer> serverMap = listMaptoMap(listMap);
        for(Server server:servers){
            if(server.getValue().equals("off")){
                serverMap.put(server.getId(),serverMap.getOrDefault(server.getId(),0)+1);
                server.setOffLineCount(serverMap.get(server.getId()));
                serverMapper.updateServerOff(server);
            }
        }
        return true;
    }

    public Map<String,Integer> serverListOff(){
        List<Map<String,Integer>> listMap = serverMapper.serverMapOff();
        return listMaptoMap(listMap);
    }

    public List<Server> serverList(){
        return serverMapper.serverList();
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

    public boolean doSecKill(String productId){
        if(productId==null){
            return false;
        }
        Jedis jedis = new Jedis("192.168.186.135",6389);
        jedis.auth("redisUSER123!");
        String kcKey = "sk:"+productId+":qt";
        String userKey = "sk:"+productId+":user";
        String kc = jedis.get(kcKey);
        String kc2 = "";
        char[] kk = kc.toCharArray();
        for(Character c:kk){
            if(c<=100){
                kc2 += c;
            }
        }
        Integer kcNum = Integer.parseInt(kc2);
        System.out.println(kcNum);
        String uid = "cxr";
        if(kc==null || jedis.sismember(userKey, uid) || kcNum<1){
            System.out.println("秒杀失败");
            jedis.close();
            return false;
        }
        jedis.decr(kcKey);
        jedis.sadd(userKey,uid);
        System.out.println("秒杀成功");
        jedis.close();
        return true;
    }

    public Postdata setPostDb(){
        Conditions conditions = new Conditions();
        List<String> list = new ArrayList<>();
        list.add("PCServer");
        list.add("Switch");
        conditions.setField("classCode");
        conditions.setOperator("IN");
        conditions.setValue(list);
        Postdata postData = new Postdata();
//        postData.setPageSize(2);
        postData.setPageSize(200000);
        postData.setNeedCount(true);
        postData.setPageNum(0);
        Conditions[] co1 = new Conditions[]{conditions};
        postData.setConditions(co1);
        return postData;
    }

}
