package com.sungcor.exam.utils;

import com.sungcor.exam.entity.Address;
import com.sungcor.exam.entity.Student;
import io.swagger.models.auth.In;

import java.util.Optional;

public class StudentTest {
    public static void main(String[] args){
        String s = "10";
        Integer i = Integer.parseInt(s);
        System.out.println(i);
//        Student student = new Student();
//        student.setId("cxr");
//        student.setNo(20183061);
//        Address address = new Address();
//        address.setCity("Shanghai");
//        address.setRoad("South Yanggao Road");
//        address.setLane("3340");
//        student.setAddress(address);
//        student = null;
//        String s = null;
//        String s1 = s+"abc";
//        System.out.println((String) null);
//
//        try {
//            System.out.println(student.getAddress().getCity());
//        } catch (Exception e) {
//            System.out.println("取值异常");
//        }

    }

    public static String getCity(Student student) throws Exception{
        return Optional.ofNullable(student)
                .map(u-> u.getAddress())
                .map(a->a.getCity())
                .orElseThrow(()->new Exception("取值错误"));
    }

}
