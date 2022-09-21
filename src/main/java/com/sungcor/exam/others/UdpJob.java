package com.sungcor.exam.others;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


@Component
@Slf4j
public class UdpJob {

    private static final String sysLogIp = "192.168.1.105";
    private static final Integer sysLogPort = 8200;

    @XxlJob("receiveData")
    public void receiveData() throws IOException {
        // 1.创建服务器端DatagramSocket，指定端口
        DatagramSocket socket = new DatagramSocket(sysLogPort);
        // 2.创建数据报，用于接收客户端发送的数据
        byte[] data = new byte[1024];// 创建字节数组，指定接收的数据包的大小
        DatagramPacket packet = new DatagramPacket(data, data.length);
        // 3.接收客户端发送的数据
        log.info("服务器端已经启动，等待客户端发送数据");
        socket.receive(packet);// 此方法在接收到数据报之前会一直阻塞
        // 4.读取数据
        String info = new String(data, 0, packet.getLength());
        log.info("我是服务器，客户端说：" + info);
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
        socket.send(packet2);
        // 4.关闭资源
        socket.close();
    }

    @XxlJob("sendData")
    public void sendData() throws IOException {
        InetAddress address = InetAddress.getByName(sysLogIp);
        int port = sysLogPort;
        byte[] data = "用户名：admin;密码：123".getBytes();
        // 2.创建数据报，包含发送的数据信息
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        // 3.创建DatagramSocket对象
        DatagramSocket socket = new DatagramSocket();
        // 4.向服务器端发送数据报
        socket.send(packet);
        /*
         * 接收服务器端响应的数据
         */
        // 1.创建数据报，用于接收服务器端响应的数据
        byte[] data2 = new byte[1024];
        DatagramPacket packet2 = new DatagramPacket(data2, data2.length);
        // 2.接收服务器响应的数据
        socket.receive(packet2);
        // 3.读取数据
        String reply = new String(data2, 0, packet2.getLength());
        log.info("我是客户端，服务器说：" + reply);
        // 4.关闭资源
        socket.close();
    }

}

