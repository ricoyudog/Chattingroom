import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.io.*;


public class MainFrame extends JFrame {
    public static String user="";
    public static String ip="";
    public static int port=0;
    public static int svr_port=6666;
    public static String svr_ip="192.168.1.7";
    public static Map<String, String> ip_name=new HashMap<String, String>();
    public static Map<String, Integer> ip_port=new HashMap<String, Integer>();

    public  MainFrame()
    {

        JFrame jf=new JFrame();
        jf.setTitle("Whispers");
        jf.setSize(500,200);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(new BorderLayout());
        jf.setBackground(Color.DARK_GRAY);
        JLabel l_logan=new JLabel("Whispers          ");
        l_logan.setForeground(Color.orange);
        l_logan.setBounds(0,100,600,50);
        l_logan.setFont(new java.awt.Font("Dialog", 1, 40));
        Box b_name=Box.createHorizontalBox();
        Box b_choose=Box.createHorizontalBox();
        Box b_frame=Box.createVerticalBox();
        Box b_ip = Box.createHorizontalBox();
        Box b_port=Box.createHorizontalBox();
        ip_name.put("192.168.3.183","mhr");
        ip_name.put("192.168.0.1","szk");
        ip_name.put("192.168.1.7","syh");
        ip_port.put("192.168.3.183",6666);
        ip_port.put("192.168.0.1",7777);
        ip_port.put("192.168.1.7",8888);
        jf.add(b_frame,BorderLayout.CENTER);
        JButton btn_chat=new JButton("chat");
        JButton btn_file=new JButton("file");
        JButton btn_game=new JButton("game");
        JTextField  name_text=new JTextField("",5);
        name_text.setPreferredSize(new Dimension(10,15));
        JLabel jl=new JLabel("name:");
        JLabel l_ip=new JLabel("ip: ");
        JLabel l_port=new JLabel("port: ");
        JTextField  ip_text=new JTextField("",3);
        JTextField  port_text=new JTextField("",2);
        b_ip.add(Box.createHorizontalStrut(40));
        b_ip.add(l_ip);
        b_ip.add(Box.createHorizontalStrut(41));
        b_ip.add(ip_text);
        b_ip.add(Box.createHorizontalStrut(20));
        b_port.add(Box.createHorizontalStrut(40));
        b_port.add(l_port);
        b_port.add(Box.createHorizontalStrut(27));
        b_port.add(port_text);
        b_port.add(Box.createHorizontalStrut(20));
        jl.setFont(new java.awt.Font("Dialog", 1, 15));
        jl.setForeground(Color.BLACK);
        btn_chat.setBackground(Color.white);
        //btn_chat.setEnabled(false);
        btn_file.setBackground(Color.white);
        btn_game.setBackground(Color.white);
        //btn_file.setEnabled(false);
        //new text_listen(btn_chat,btn_file).start();
        b_frame.add(l_logan);
       // b_frame.add(Box.createVerticalStrut(40));
       // b_frame.add(l_logan);
        b_name.add(Box.createHorizontalStrut(40));
        b_name.add(jl);
        b_name.add(Box.createHorizontalStrut(15));
        b_name.add(name_text);
        b_name.add(Box.createHorizontalStrut(20));
        b_name.add(Box.createHorizontalGlue());
        b_frame.add(b_name);
        b_frame.add(b_ip);
        b_frame.add(b_port);
        b_choose.add(Box.createHorizontalStrut(190));
        b_choose.add(btn_chat);
        b_choose.add(btn_file);
        b_choose.add(btn_game);
        b_name.add(Box.createVerticalStrut(40));
        b_frame.add(b_choose);
        b_frame.add(Box.createVerticalStrut(30));
        btn_chat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // ip_name.put("127.0.0.1",user);
                //ip_port.put("127.0.0.1",port);
                chat c=new chat();
                usr2 usr=new usr2();
                usr.open();
                try {
                    DatagramSocket log_socket=new DatagramSocket();
                    String s_port=String.valueOf(port);
                    String rep_sev=user+"/"+s_port;
                    DatagramPacket packet = new DatagramPacket(rep_sev.getBytes(), rep_sev.getBytes().length, InetAddress.getByName(svr_ip), svr_port);
                    log_socket.send(packet);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }


            }
        });
        btn_game.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] arguments = new String[] {"python", "/Users/guanyuhang/Desktop/whisper/src/connect4.py"};


                try {
                    Process process = Runtime.getRuntime().exec(arguments);
                    BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
                    String line = null;
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                    }
                    in.close();
                    //java代码中的process.waitFor()返回值为0表示我们调用python脚本成功，
                    //返回值为1表示调用python脚本失败，这和我们通常意义上见到的0与1定义正好相反
                    int re = process.waitFor();
                    System.out.println(re);
                } catch (IOException er) {
                    er.printStackTrace();
                } catch (InterruptedException er) {
                    er.printStackTrace();
                }
            }
        });
        btn_file.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                file_send f = new file_send();


            }
        });
        name_text.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    user=e.getDocument().getText(0,e.getDocument().getLength());
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        ip_text.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    ip=e.getDocument().getText(0,e.getDocument().getLength());
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        port_text.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    String p;
                    p=e.getDocument().getText(0,e.getDocument().getLength());
                    port=Integer.parseInt(p);
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        jf.pack();
        jf.setVisible(true);

    }
    /*class text_listen extends Thread{
        JButton chat;
        JButton file;
        text_listen(JButton chat,JButton file){
            this.chat=chat;
            this.file=file;
        }


        public void run(){
            while(true) {
                if (user == "" || ip == "" || port == 0) {
                    chat.setEnabled(false);
                    file.setEnabled(false);
                } else {
                    chat.setEnabled(true);
                    file.setEnabled(true);

                }
            }
        }

    }
*/
    public static void main(String[] args) {



        usr2 user=new usr2();
        user.open();
        MainFrame mf=new MainFrame();
        file_socket fs=new file_socket();
        fs.start();
        //chat c=new chat();


    }





}
