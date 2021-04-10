import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@SuppressWarnings("unchecked")
public class file_send extends JFrame {
    long process = 0;
    public file_send() {
        JFrame jf = new JFrame();

        jf.setTitle("Whispers");
        jf.setSize(855, 250);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(new BorderLayout());
        jf.setBackground(Color.DARK_GRAY);
        JLabel l_logan = new JLabel("Whispers               ");
        JLabel l_name = new JLabel("receiver:   ");
        JLabel l_file = new JLabel("file:          ");
        JTextField jtf_file = new JTextField(25);
        l_logan.setForeground(Color.orange);
        l_logan.setBounds(0, 100, 600, 50);
        l_logan.setFont(new java.awt.Font("Dialog", 1, 40));

        Box b_name = Box.createHorizontalBox();
        Box b_choose = Box.createHorizontalBox();
        Box b_btnfile = Box.createHorizontalBox();
        Box b_frame = Box.createVerticalBox();
        jf.add(b_frame, BorderLayout.CENTER);
        JButton btn_file = new JButton("choose file");
        JButton btn_send = new JButton("send");
        JButton btn_close = new JButton("close");
        JButton btn_decrypt = new JButton("decrypt");
        JButton btn_encrypt= new JButton("encryption");
        JComboBox jc_rec = new JComboBox();
        jc_rec.addItem("--please choose a reveiver--");
        for (String key : MainFrame.ip_name.keySet()) {
            jc_rec.addItem(MainFrame.ip_name.get(key));

        }
        jc_rec.addItem("test");
        btn_file.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int val = fc.showOpenDialog(null);    //open dialog
                if (val == fc.APPROVE_OPTION) {
                    //choose file
                    jtf_file.setText(fc.getSelectedFile().toString());
                } else {

                    jtf_file.setText("not choose file");
                }
            }
        });
        btn_close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btn_send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //send file
                String name = jc_rec.getEditor().getItem().toString().trim();
                String ip = "";
                for (String key : MainFrame.ip_name.keySet()) {
                    if (name.equals(MainFrame.ip_name.get(key))) {
                        ip = key;
                    }
                }
                if(name.equals("test")){
                    ip="192.168.0.103";
                }
                System.out.println(ip);


                try {
                    Socket socket = new Socket(ip, 10010);
                    File sel_file = new File(jtf_file.getText());
                    BufferedReader buffer =new BufferedReader(new FileReader(sel_file));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                    String line1 =null;
                    while((line1=buffer.readLine())!=null)
                    {
                        out.println(line1);
                        System.out.println("client:  "+line1);
                    }
                    BufferedReader bufin = new BufferedReader(new InputStreamReader
                            (socket.getInputStream()));
                    socket.shutdownOutput();
                    String result=bufin.readLine();
                    System.out.println("client receive file");
                    System.out.println(result);
                    buffer.close();
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    btn_encrypt.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] arguments = new String[] {"python", "/Users/guanyuhang/Desktop/whisper/src/encryption.py"};


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
        btn_decrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] arguments = new String[] {"python", "/Users/guanyuhang/Desktop/whisper/src/decryption.py"};


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
        b_name.add(Box.createHorizontalStrut(30));
        b_name.add(l_name);
        b_name.add(jc_rec);
        b_name.add(Box.createHorizontalStrut(30));
        b_choose.add(Box.createHorizontalStrut(30));
        b_choose.add(l_file);
        b_choose.add(jtf_file);
        b_choose.add(Box.createHorizontalStrut(32));
        b_btnfile.add(Box.createHorizontalStrut(120));
        b_btnfile.add(btn_file);
        b_btnfile.add(Box.createHorizontalStrut(75));
        b_btnfile.add(btn_send);
        b_btnfile.add(Box.createHorizontalStrut(75));
        b_btnfile.add(btn_close);
        b_btnfile.add(Box.createHorizontalStrut(75));
        b_btnfile.add(btn_encrypt);
        b_btnfile.add(Box.createHorizontalStrut(75));
        b_btnfile.add(btn_decrypt);
        b_frame.add(l_logan);
        b_frame.add(Box.createVerticalStrut(10));
        b_frame.add(b_name);
        b_frame.add(Box.createVerticalStrut(20));
        b_frame.add(b_choose);
        b_frame.add(Box.createVerticalStrut(20));
        b_frame.add(b_btnfile);
        b_frame.add(Box.createVerticalStrut(40));
        jf.add(b_frame);


        jf.setVisible(true);


    }

    class Progress extends Thread {
        JProgressBar progressBar;
        JButton button;

        Progress(JProgressBar progressBar, JButton button) {
            this.progressBar = progressBar;
            this.button = button;
        }

        public void run() {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //设置进度条的值
                progressBar.setValue((int) process);
            }
            progressBar.setIndeterminate(false);
            progressBar.setString("升级完成！");
            button.setEnabled(true);


        }
    }

    class reveive extends Thread{


        public void run(){

            try {
                ServerSocket serverSocket = new ServerSocket(MainFrame.port);
                Socket socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }





    }

}