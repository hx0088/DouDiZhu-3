package doudizhu;

import javax.swing.*;
import java.awt.*;

public class testbuttom {
    private JFrame f=new JFrame("demo01");
    public Container container = null;
    JButton chupai=new JButton("出牌");
    JPanel p=new JPanel();
    public static void main (String[] args) throws Exception {
        testbuttom start=new testbuttom();
        start.initial();

    }
    public void initial(){
        container = f.getContentPane();
        container.setLayout(null);
        f.setSize(600,600);
        f.setResizable(false);
        chupai.setBounds(100,100,30,30);
        chupai.setVisible(true);
        container.add(chupai);
        f.setVisible(true);

    }
}
