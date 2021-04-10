import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class file_socket extends  Thread {
    public  void run() {

        try {

            ServerSocket ss = new ServerSocket(10010);
            while (true) {
                Socket s = ss.accept();

                BufferedReader bufin = new BufferedReader(new InputStreamReader(s.getInputStream()));

                BufferedWriter bufw = new BufferedWriter(new FileWriter("/Users/guanyuhang/Desktop/new.txt"));

                String line1 = null;

                while ((line1 = bufin.readLine()) != null) {
                    bufw.write(line1, 0, line1.length());
                }

                System.out.println("服务端接收完了。。。。");
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                out.println("上传成功");
                System.out.println("服务端反馈客户端完了。。。。");

                bufw.close();


                //s.close();
                //ss.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }


}
