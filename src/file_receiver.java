import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@SuppressWarnings("unchecked")
public class file_receiver extends JFrame {
    String path="";
    public file_receiver(){

        JFrame jf_dialog=new JFrame();
        JFrame jf_receive=new JFrame();
        jf_dialog.setTitle("Whispers");
        jf_dialog.setSize(355, 250);
        jf_dialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf_dialog.setLayout(new BorderLayout());
        jf_dialog.setBackground(Color.DARK_GRAY);
        jf_receive.setTitle("Whispers");
        jf_receive.setSize(500,200);
        jf_receive.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf_receive.setLayout(new BorderLayout());
        jf_receive.setBackground(Color.DARK_GRAY);
        JButton diabtn_rej=new JButton("reject");
        JButton diabtn_ok=new JButton("agree");
        JLabel l_dialog= new JLabel("Do you want to receive a file from  ");
        JLabel l_dialogan = new JLabel("Whispers          ");
        Box b_path=Box.createHorizontalBox();
        JLabel jl_path=new JLabel("path:");
        JTextField  path_text=new JTextField("",3);
        b_path.add(Box.createHorizontalStrut(40));
        b_path.add(jl_path);
        jl_path.setFont(new java.awt.Font("Dialog", 1, 15));
        jl_path.setForeground(Color.BLACK);
        b_path.add(Box.createHorizontalStrut(15));
        b_path.add(path_text);
        b_path.add(Box.createHorizontalStrut(20));
        b_path.add(Box.createHorizontalGlue());
        path_text.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    path=e.getDocument().getText(0,e.getDocument().getLength());
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });


        l_dialogan.setForeground(Color.orange);
        l_dialogan.setBounds(0, 100, 600, 50);
        l_dialogan.setFont(new java.awt.Font("Dialog", 1, 40));
        Box b_dialogbtn=Box.createHorizontalBox();
        Box b_dialog=Box.createVerticalBox();
        Box b_dialogmes=Box.createHorizontalBox();
        Box b_dialoglogan=Box.createHorizontalBox();
        b_dialoglogan.add(Box.createHorizontalStrut(90));
        b_dialoglogan.add(l_dialogan);

        b_dialogbtn.add(Box.createHorizontalStrut(20));
        b_dialogbtn.add(diabtn_rej);
        b_dialogbtn.add(Box.createHorizontalStrut(50));
        b_dialogbtn.add(diabtn_ok);

        b_dialogmes.add(Box.createHorizontalStrut(20));
        b_dialogmes.add(l_dialog);
        b_dialog.add(b_dialoglogan);
        b_dialog.add(b_dialogmes);
        b_dialog.add(Box.createVerticalStrut(20));
        b_dialog.add(b_dialogbtn);
        jf_dialog.add(b_dialog);
        jf_dialog.setVisible(true);
       /* try {
            ServerSocket ss = new ServerSocket(10010);
            Socket s =ss.accept();
            jf_dialog.setVisible(true);
            diabtn_ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        BufferedReader bufin = new BufferedReader(new InputStreamReader(s.getInputStream()));

                        BufferedWriter bufw =new BufferedWriter(new FileWriter(path));
                        String line1 =null;

                        while((line1=bufin.readLine())!=null)
                        {
                            bufw.write(line1,0,line1.length());
                        }
                        bufw.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            System.out.println("服务端接收完了。。。。");
            PrintWriter out = new PrintWriter(s.getOutputStream(),true);
            out.println("上传成功");
            System.out.println("服务端反馈客户端完了。。。。");




            s.close();
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/


    }


}