package com.kronos.chapp;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Server implements ActionListener{
    
    JPanel p1;
    JTextField t1;
    JButton b1;
    static JPanel a1;
    static JFrame f1 = new JFrame();
    
    static Box vertical = Box.createVerticalBox();
    
    static ServerSocket skt;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    
    Boolean typing;
    
    // this constructor contains all the code for the Frame
    Server(){
        f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(Color.DARK_GRAY);
        p1.setBounds(0, 0, 450, 70);
        f1.add(p1);
        
       ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("com/kronos/chapp/icons/3.png"));
       Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
       ImageIcon i3 = new ImageIcon(i2);
       JLabel l1 = new JLabel(i3);
       l1.setBounds(5, 22, 30, 30);
       p1.add(l1);
       
       l1.addMouseListener(new MouseAdapter(){
           public void mouseClicked(MouseEvent ae){
               System.exit(0);
           }
       });
       
       ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("com/kronos/chapp/icons/male.png"));
       Image i5 = i4.getImage().getScaledInstance(55, 55, Image.SCALE_DEFAULT);
       ImageIcon i6 = new ImageIcon(i5);
       JLabel l2 = new JLabel(i6);
       l2.setBounds(45, 8, 55, 55);
       p1.add(l2);
       
             
       JLabel l3 = new JLabel("User 1");
       l3.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
       l3.setForeground(Color.WHITE);
       l3.setBounds(110, 15, 100, 20);
       p1.add(l3);   
       
       
       JLabel l4 = new JLabel("Active Now");
       l4.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
       l4.setForeground(Color.WHITE);
       l4.setBounds(110, 40, 100, 14);
       p1.add(l4);  
       
       Timer t = new Timer(1, new ActionListener(){
           public void actionPerformed(ActionEvent ae){
               if(!typing){
                   l4.setText("Active Now");
               }
           }
       });
       
       t.setInitialDelay(2000);
       
       
       a1 = new JPanel();
       a1.setBounds(5, 75, 440, 525);
       a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
       f1.add(a1);
       
       
       t1 = new JTextField();
       t1.setBounds(5, 610, 350, 35);
       t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
       f1.add(t1);
       
       t1.addKeyListener(new KeyAdapter(){
           public void keyPressed(KeyEvent ke){
               l4.setText("typing...");
               
               t.stop();
               
               typing = true;
           }
           
           public void keyReleased(KeyEvent ke){
               typing = false;
               
               if(!t.isRunning()){
                   t.start();
               }
           }
       });
       
       b1 = new JButton("Send");
       b1.setBounds(360, 610, 80, 35);
       b1.setBackground(Color.DARK_GRAY);
       b1.setForeground(Color.WHITE);
       b1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
       b1.addActionListener(this);
       f1.add(b1);
        
       f1.getContentPane().setBackground(Color.WHITE);
       f1.setLayout(null);
       f1.setSize(450, 650);
       f1.setLocation(100, 20); 
       f1.setUndecorated(true);
       f1.setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent ae){
        try{
            String out = t1.getText();
            
            JPanel p2 = formatLabel(out);
            
            a1.setLayout(new BorderLayout());
            
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            
            a1.add(vertical, BorderLayout.PAGE_START);
            
            //a1.add(p2);
            dout.writeUTF(out);
            t1.setText("");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static JPanel formatLabel(String out){
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        
        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">"+out+"</p></html>");
        l1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        l1.setBackground(Color.LIGHT_GRAY);
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15,15,15,50));
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));
        
        p3.add(l1);
        p3.add(l2);
        return p3;
    }
    
    public static void main(String[] args){
        new Server().f1.setVisible(true);
        
        String msginput = "";
        try{
            skt = new ServerSocket(6001);
            while(true){
                s = skt.accept();
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
            
	        while(true){
	                msginput = din.readUTF();
                        JPanel p2 = formatLabel(msginput);
                        
                        JPanel left = new JPanel(new BorderLayout());
                        left.add(p2, BorderLayout.LINE_START);
                        vertical.add(left);
                        f1.validate();
            	}
                
            }
            
        }catch(Exception e){}
    }    
}
