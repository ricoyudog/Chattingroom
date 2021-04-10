import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class chat {
    public String message;
    int p_panel_y=0;
    public static int message_pos;
    public static JPanel p_online=new JPanel();
    public static JPanel p_chat= new JPanel();
    public chat(){
        String start="This is a Start ,have a good night !";
        JFrame jf=new JFrame();
        jf.setTitle("Chat room");
        jf.setSize(800,600);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // jf.setVisible(true);
      //  FlowLayout  flow=new FlowLayout( );
        JPanel p_infor= new JPanel();
        //JPanel p_online=new JPanel();
        p_online.setLayout(null);
        p_chat.setLayout(null);
        JTextField t_input= new JTextField("please input message" ,5);
        Button btn_message= new Button("send");
        Button btn_file=new Button("file");
        JLabel l_online = new JLabel("online user: ");
        JLabel usr=new JLabel(MainFrame.user);
        JLabel l_start=new JLabel(start);
        JLabel l_logan=new JLabel("Whispers");
        JScrollPane j_pane= new JScrollPane(p_chat);
        j_pane.setPreferredSize(new Dimension(700,400));
        //System.out.println(MainFrame.user);
        usr.setFont(new java.awt.Font("Dialog", 1, 20));
        l_online.setFont(new java.awt.Font("Dialog", 3, 20));
        l_start.setFont(new java.awt.Font("Dialog", 5, 20));
        l_start.setForeground(Color.PINK);
        l_online.setForeground(Color.BLUE);
        p_chat.add(l_start);
        l_start.setBounds(30,0,400,50);
        t_input.setPreferredSize(new Dimension(500,100));
        t_input.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    message=e.getDocument().getText(0,e.getDocument().getLength());

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
        p_chat.setPreferredSize(new Dimension(600,0));
        p_chat.revalidate();
        p_chat.setBackground(Color.white);
        p_chat.setLayout(null);
        p_chat.setFont(new java.awt.Font("Dialog", 1, 15));
        btn_message.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date=new Date();
                String s_df=df.format(date);
                JLabel usr_name=new JLabel("   "+MainFrame.user+"( ip:"+MainFrame.ip+"  port: "+MainFrame.port+" )"+" "+s_df);
                JLabel self_message=new JLabel("   "+message);

                p_chat.add(self_message);
                p_chat.add(usr_name);
                usr_name.setForeground(Color.BLUE);
                usr_name.setFont(new java.awt.Font("Dialog", 3, 15));
                message_pos=message_pos+40;
                usr_name.setBounds(0,message_pos,800,50);
                message_pos=message_pos+20;
                self_message.setBounds(0,message_pos,800,50);
                self_message.setFont(new java.awt.Font("Dialog", 1, 15));
               // message_pos=message_pos+20;

                t_input.setText("");
                p_chat.setPreferredSize(new Dimension(900,message_pos+60));
*/
                /*send message to others*/
                try {
                    if(MainFrame.ip_name.isEmpty()){

                    }
                    else {
                        DatagramSocket socket = new DatagramSocket();
                        for(String key:MainFrame.ip_port.keySet()) {
                            try {
                                String send_mes="   "+message;
                                DatagramPacket packet = new DatagramPacket(send_mes.getBytes(),
                                        send_mes.getBytes().length, InetAddress.getByName(key),
                                        MainFrame.ip_port.get(key));
                                socket.send(packet);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


            }
        });
        p_online.setPreferredSize(new Dimension(300,400));
        p_online.setBackground(Color.LIGHT_GRAY);
        p_online.setLayout(null);
        p_online.add(l_online);
        p_online.add(usr);

        l_online.setBounds(50,p_panel_y,400,50);
        p_panel_y=p_panel_y+20;
        usr.setBounds(50,p_panel_y,400,50);
        usr.setForeground(Color.GREEN);
        for(String key:MainFrame.ip_name.keySet()){
           String name= MainFrame.ip_name.get(key);
           if(name.equals(MainFrame.user)){

           }
           else {
               JLabel user = new JLabel(name);
               p_panel_y = p_panel_y + 20;
               user.setBounds(50, p_panel_y, 400, 50);
               user.setForeground(Color.yellow);
               user.setFont(new java.awt.Font("Dialog", 1, 20));
               p_online.add(user);
           }

        }
        p_infor.setPreferredSize(new Dimension(800,100));
        p_infor.setBackground(Color.GRAY);
        p_infor.setLayout(null);
        p_infor.add(l_logan);
        l_logan.setForeground(Color.orange);
        l_logan.setBounds(300,20,400,50);
        l_logan.setFont(new java.awt.Font("Dialog", 1, 50));
        Box b_main= Box.createVerticalBox();
        Box b_middle=Box.createHorizontalBox();
        Box b_down=Box.createHorizontalBox();
        Box b_btn=Box.createVerticalBox();
        b_main.add(p_infor);
       // b_main.add(Box.createVerticalStrut(100));
        b_middle.add(p_online);
        b_middle.add(j_pane);
        b_main.add(b_middle);
        b_btn.add(btn_file);
        b_btn.add(btn_message);
        b_down.add(t_input);
        b_down.add(b_btn);
        b_main.add(b_down);







        jf.add(b_main);
        jf.setVisible(true);





    }
}
