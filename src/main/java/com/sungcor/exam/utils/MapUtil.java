package com.sungcor.exam.utils;
import com.sun.org.apache.xml.internal.res.XMLErrorResources_tr;

import java.util.*;

public class MapUtil{
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescending(Map<K, V> map)
    {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        list.sort((o1, o2) -> {
            int compare = (o1.getValue()).compareTo(o2.getValue());
            return -compare;
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
//    public static int ranDom(){
//        return (int) (900000*Math.random()+100000);
//    }
//
//
//    public static void main(String[] args) {
//        for(int i=0;i<=20;i++){
//            System.out.println(ranDom());
//        }
//    }
}

