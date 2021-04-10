import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class svr {
    public static void main(String[] args) {
            new svr_r().start();
            System.out.println("start!!");


    }


}

 class svr_r extends Thread{
     Map<String, String> ip_name=new HashMap<String, String>();
     Map<String, Integer> ip_port=new HashMap<String, Integer>();
    public void run(){
        try {
            DatagramSocket socket = new DatagramSocket(6666);
            while(true) {
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                socket.receive(packet);
                byte[] arr = packet.getData();
                int len = packet.getLength();
                String data = new String(arr);
                System.out.println("new message：   "+data);
                String ip= packet.getAddress().getHostAddress().trim();
               // String [] str=data.split("/");
                //String name=str[0].trim();
                //String s_port=str[1].trim();
                //int port=Integer.parseInt(s_port);

                if("signoff".equals(data.trim())){
                    String signal="$remove/"+ip+"/"+ip_name.get(ip)+"/"+ip_port.get(ip).toString();
                    ip_name.remove(ip);
                    ip_port.remove(ip);
                    DatagramSocket usr_socket=new DatagramSocket();
                    for(String key:ip_port.keySet()) {
                        DatagramPacket usr_packet = new DatagramPacket(signal.getBytes(), signal.getBytes().length, InetAddress.getByName(key),ip_port.get(key) );
                        usr_socket.send(usr_packet);
                    }


                }
                else{

                    //ip_name.put(ip,name);
                    //ip_port.put(ip,port);
                    String [] str=data.split("/");
                    String name=str[0].trim();
                    String s_port=str[1].trim();
                    int port=Integer.parseInt(s_port);
                    String signal="$login/"+ip+"/"+name+"/"+s_port;
                    System .out.println("send signal:  "+signal);
                    //System.out.println(ip_port.get(ip));
                    DatagramSocket usr_socket=new DatagramSocket();

                        ip_name.put(ip,name);
                        ip_port.put(ip,port);
                        System.out.println("first user log in!");
                    for(String key:ip_port.keySet()) {
                        System.out.println(key);
                        System.out.println("online user:"+key+ip_port.get(key));
                        DatagramPacket usr_packet = new DatagramPacket(signal.getBytes(), signal.getBytes().length, InetAddress.getByName(key),ip_port.get(key) );
                        //usr_socket.send(usr_packet);

                    }
                    /*String exist_host="$start!";
                    for(String key: ip_name.keySet()){
                        exist_host=exist_host+key+"/"+ip_name.get(key)+"/"+ip_port.get(key).toString()+"!";

                    }
                    DatagramPacket usr_packet = new DatagramPacket(exist_host.getBytes(), exist_host.getBytes().length, InetAddress.getByName(ip),port );
                    usr_socket.send(usr_packet);
*/


                }
            }
            // socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}