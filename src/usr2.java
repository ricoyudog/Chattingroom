import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class usr2 {
    public static String rec_mess;
    public class r extends Thread{
        public void run(){
            try {
                System.out.println("*******client receiver init!!");
                DatagramSocket socket = new DatagramSocket(MainFrame.port);
                while(true) {
                    DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                    socket.receive(packet);
                    byte[] arr = packet.getData();
                    int len = packet.getLength();
                    String data = new String(arr);

                    String ip= packet.getAddress().getHostAddress().trim();
                    System.out.println("receive message:   "+data+"   ip:"+ip);
                    int port = packet.getPort();
                    if(data.length()>1&&ip.equals(MainFrame.svr_ip) && data.charAt(6)=='!'){
                        String[] str=data.split("!");
                        for(int i=1;i< str.length;i++){
                            String [] sub=str[i].split("/");
                            String temp_ip=sub[0].trim();
                            String temp_name= sub[1].trim();
                            String temp_port = sub[2].trim();
                            MainFrame.ip_name.put(temp_ip,temp_name);
                            MainFrame.ip_port.put(temp_ip,Integer.parseInt(temp_port));

                        }


                    }

                    else if(data.length()>1&&ip.equals(MainFrame.svr_ip)){
                        String[] sub=data.split("/");
                        if(sub[0].equals("$login")) {
                            String temp_ip = sub[1].trim();
                            String temp_name = sub[2].trim();
                            String temp_port = sub[3].trim();
                            MainFrame.ip_name.put(temp_ip, temp_name);
                            MainFrame.ip_port.put(temp_ip, Integer.parseInt(temp_port));
                        }

                      /*  else if(sub[0].equals("$remove")){
                            String temp_ip = sub[1].trim();
                            String temp_name = sub[2].trim();
                            String temp_port = sub[3].trim();
                            MainFrame.ip_name.remove(temp_ip, temp_name);
                            MainFrame.ip_port.remove(temp_ip, Integer.parseInt(temp_port));

                        }

                       */

                    }
                    //receive others message and make it show in chat panel
                    else{
                        String name ="";
                        if(MainFrame.ip_name.isEmpty()){
                            name="";
                        }
                        else{
                            name=MainFrame.ip_name.get(ip);
                            System.out.println("received from  "+data);
                        }
                        JLabel mes=new JLabel(data);
                        System.out.println("received mess  "+data);
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date=new Date();
                        String s_df=df.format(date);
                        System.out.println("ip: "+ip+"port: "+port);
                        JLabel usr_name=new JLabel("   "+name+"( ip:"+ip+"  port: "+MainFrame.ip_port.get(ip)+" )"+" "+s_df);
                        chat.p_chat.add(usr_name);
                        chat.p_chat.add(mes);

                        if(ip.equals(MainFrame.ip)) {
                            usr_name.setForeground(Color.RED);
                        }
                        else{
                            usr_name.setForeground(Color.BLUE);

                        }
                        usr_name.setFont(new java.awt.Font("Dialog", 3, 15));
                        chat.message_pos=chat.message_pos+30;
                        usr_name.setBounds(0,chat.message_pos,800,50);
                        chat.message_pos=chat.message_pos+20;
                        mes.setBounds(0,chat.message_pos,800,50);
                        mes.setFont(new java.awt.Font("Dialog", 1, 15));
                        chat.p_chat.setPreferredSize(new Dimension(900,chat.message_pos+60));


                    }


                    System.out.println("sender_ip: "+ip+"  sender_port: "+port+"  message: "+data);



                }
                // socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }




    }

    public  class s extends Thread{
        public void run(){
            try {
                Scanner input = new Scanner(System.in);
                DatagramSocket socket=new DatagramSocket();
                while(true) {
                    String line = input.nextLine();
                    DatagramPacket packet = new DatagramPacket(line.getBytes(), line.getBytes().length, InetAddress.getByName(MainFrame.ip), MainFrame.port);
                    socket.send(packet);
                    if("quit".equals(line)){
                        break;
                    }
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }




    }
    public  void  open(){
        new r().start();
       // new s().start();


    }




}



