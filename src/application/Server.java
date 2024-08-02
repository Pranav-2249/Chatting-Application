package application;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.*;

public class Server implements ActionListener{

    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;

    Server()// Constructor is called immediately after the object is created 
    {
        f.setLayout(null);// we are not using inbuilt layout


        JPanel p1 = new JPanel();// creating the user defined layout
        p1.setBackground(new Color(7,94,84)); // background colour 
        p1.setBounds(0,0,450,70); // boundaries of the upper green colour frame 
        p1.setLayout(null);
        f.add(p1); // add the layout to the existing frame

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/BlackArrow.png"));// adding the arrow image to the layout
        Image i2 = i1.getImage().getScaledInstance(15,15,Image.SCALE_DEFAULT); // adjusting the image properly
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3); // i2 cannot be passed directly so we are creating a new object i3 and passiing i2 in it 
        back.setBounds(5,20,25,25); // setting boundaries of the arrow image 
        p1.add(back);// we want to add the image above the layout which is built on the frame so p1.add()

        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }

        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icon/about-me.png"));// adding my profile image
        Image i5 = i4.getImage().getScaledInstance(30,30 ,Image.SCALE_DEFAULT); 
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);  
        profile.setBounds(40,10,50,50); 
        p1.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icon/video.png"));// adding video icon
        Image i8 = i7.getImage().getScaledInstance(20,20 ,Image.SCALE_DEFAULT); 
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);  
        video.setBounds(300,20,30,30); 
        p1.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icon/phone.png"));// adding phone icon
        Image i11 = i10.getImage().getScaledInstance(20,20 ,Image.SCALE_DEFAULT); 
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);  
        phone.setBounds(360,20,35,30); 
        p1.add(phone);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icon/3icon.png"));// adding 3icon image
        Image i14 = i13.getImage().getScaledInstance(10,15 ,Image.SCALE_DEFAULT); 
        ImageIcon i15 = new ImageIcon(i14);
        JLabel dots = new JLabel(i15);  
        dots.setBounds(420,20,10,25); 
        p1.add(dots);

        JLabel name = new JLabel("Pranav");// adding text to the frame
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD, 18));
        p1.add(name);

        JLabel status = new JLabel("Active Now");// adding text to the frame
        status.setBounds(110, 35, 100, 18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD, 14));
        p1.add(status);

        a1 = new JPanel();// creating the panel for the text purpose
        a1.setBounds(5, 75, 440, 570);
        a1.setLayout(new BorderLayout());
        f.add(a1);

        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN, 16));
        f.add(text);

        JButton Send = new JButton("Send");
        Send.setBounds(320, 655, 123, 40);
        Send.setBackground(new Color(7,94,84));
        Send.setForeground(Color.WHITE);
        Send.addActionListener(this);
        Send.setFont(new Font("SAN_SERIF", Font.PLAIN,16));
        f.add(Send);




        f.setSize(450, 700);// below this everything is for the outer frame purpose
        f.setLocation(200, 50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        try{
            String out = text.getText();
    
            JPanel p2 = formatlabel(out);
        
            a1.setLayout(new BorderLayout());
    
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2 , BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);
    
            dout.writeUTF(out);
    
            text.setText("");
    
            f.repaint();
            f.invalidate();
            f.validate();
        } catch(Exception e){
            e.printStackTrace();
        }
       

    }

    public static JPanel formatlabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width : 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));

        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);

        return panel;

        
    }

    public static void main(String args[]){
        new Server();// anonmous object creation because after creatin this object the server constructor gets called.

        try {

            ServerSocket skt = new ServerSocket(6001);
            while(true){
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while(true){
                    String msg = din.readUTF();
                    JPanel panel = formatlabel(msg);
                    
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }


        } catch (Exception e){
            e.printStackTrace();
        }

    }
    
}
