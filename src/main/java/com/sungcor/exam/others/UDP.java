package com.sungcor.exam.others;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class UDP {
    public static void main(String[] args) throws Exception {

        InetAddress addr1 = InetAddress.getByName("192.168.1.105");
        DatagramSocket socket = new DatagramSocket(8200,addr1);
        // 2.创建数据报，用于接收客户端发送的数据
        byte[] data = new byte[61858764];// 创建字节数组，指定接收的数据包的大小
        DatagramPacket packet = new DatagramPacket(data, data.length);
        // 3.接收客户端发送的数据
        System.out.println("****服务器端已经启动，等待客户端发送数据");
        while(true){
            socket.receive(packet);
            // 此方法在接收到数据报之前会一直阻塞
            // 4.读取数据
            String info = new String(data, 0, packet.getLength());
            System.out.println("我是服务器，客户端说：" + info);
            /*
             * 向客户端响应数据
             */
            // 1.定义客户端的地址、端口号、数据
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            byte[] data2 = "欢迎您!".getBytes();
            // 2.创建数据报，包含响应的数据信息
            DatagramPacket packet2 = new DatagramPacket(data2, data2.length, address, port);
            // 3.响应客户端
//        Thread.sleep(7000L);
            socket.send(packet2);
        }
        /*
         * 接收客户端发送的数据
         */
        // 1.创建服务器端DatagramSocket，指定端口


        // 4.关闭资源

    }
}
