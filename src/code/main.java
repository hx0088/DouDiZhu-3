package code;

import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class main extends JFrame {
    private JFrame f=new JFrame("demo01");
    public JTextField lasttime;
    timer t;
    int loadnum=-1;
    int lastchupai=0;
    int nowplay=0;
    boolean initial_mark=false;
    public Container container = null;
    JButton chupai=new JButton("出牌");
    JButton seeklord =new JButton(" 叫地主");
    JButton notseeklord= new JButton("不叫");
    JButton notchupai=new JButton(("不出"));
    CardValue LastCard=new CardValue();
    public static void main (String[] args) throws Exception {
        main start=new main();
        start.initial();
        start.readcard();
        start.setcard();
        start.printcard();

    }
    public void initial(){


        LastCard.len=0;
        container = f.getContentPane();
        container.setLayout(null);
        //f.setSize(1800,600);
        f.setResizable(false);
        t =new timer(this);

        lasttime=new JTextField();
        lasttime.setEditable(false);
        lasttime.setVisible(true);
        lasttime.setBounds(400,400,100,100);
        container.add(lasttime);
        seeklord.setBounds(700,600,100,50);
        seeklord.setVisible(true);
        seeklord.addActionListener(e->{
            findlord(0);
            t.endseekload(this,0);
            t.lasttime=30;
            initial_mark=true;
            notseeklord.setVisible(false);
            seeklord.setVisible(false);
        });
        notseeklord.setBounds(900,600,100,50);
        notseeklord.setVisible(true);
        notseeklord.addActionListener(e->{
            if(t.countVal(1)){
                getlord(1);
                t.endseekload(this,1);
            }
            else if(t.countVal(2)){
                getlord(2);
                t.endseekload(this,2);
            }
            else{

            }
            notseeklord.setVisible(false);
            seeklord.setVisible(false);
        });
        notchupai.setBounds(800,600,100,50);
        notchupai.setVisible(false);
        notchupai.addActionListener(e->{
            if(nowplay==lastchupai) return;
            nowplay++;
            nowplay%=3;
            t.lasttime=0;
            this.repaint();
        });
        chupai.setBounds(600,600,100,50);
        chupai.setVisible(false);
        chupai.addActionListener(e->{
            boolean hascard=false;
            ArrayList<Integer> CardList=new ArrayList<>();
            int r;
            if(loadnum==0) r=20;
            else r=17;
            for(int i=0;i<r;i++) {
                if(mycard[0][i].clicked==true && mycard[0][i].canClick==true){
                    hascard=true;
                    //System.out.println(mycard[0][i].weight+" "+mycard[0][i].type);
                    if(mycard[0][i].weight==16 && mycard[0][i].type==2){
                        CardList.add(mycard[0][i].weight+1);
                    }
                    else CardList.add(mycard[0][i].weight);
                }
            }
            if(!hascard) return;
            cardtype w=common.Judgetype(CardList);
            CardValue theweight=new CardValue();
            theweight.type=w;
            theweight.len=CardList.size();
            theweight.lessw=common.Judgelessw(CardList,w);
            //System.out.println(theweight.type+" "+theweight.lessw+" "+theweight.len);
            //System.out.println(w);
            /*if(LastCard.len!=0){
                //int res=CardValue.islarge(LastCard,theweight);
                //System.out.println(res);
                if(LastCard.type!=w){
                    System.out.println("与上家牌型不符");
                    this.getcardback();
                    return;
                }


            }*/
            System.out.println(LastCard.type+" "+LastCard.lessw+" "+LastCard.len);
            if((w!=cardtype.illegal && CardValue.islarge(LastCard,theweight)==1) || LastCard.len==0) {
                LastCard.len=theweight.len;
                LastCard.type=theweight.type;
                LastCard.lessw=theweight.lessw;
                lastchupai=0;
                t.lasttime=30;
                nowplay++;
                nowplay%=3;
                this.repaint();
            }
            else{
                System.out.println("不符合规则的牌型");
                this.getcardback();
            }
        });
        container.add(seeklord);
        container.add(notseeklord);
        container.add(chupai);
        container.add(notchupai);
        container.setComponentZOrder(chupai,0);
        f.setVisible(true);

    }

    BufferedImage []cards=new BufferedImage[54];
    int[]lis=new int[54];
    int cnt=0;
    public void readcard() throws Exception {
        for(int i=3;i<=16;i++){
            for(int j=1;j<=4;j++){
                if(i==16 && j>2) break;
                //System.out.println((cnt/4+3)+" "+i);
                String path="DouDiZhu\\images\\"+i+"-"+j+".gif";
                //System.out.println(path);
                File paths=new File(path);
                cards[cnt]= ImageIO.read(new File(path));
                cnt++;
            }
        }
    }
    public  void setcard(){
        Random random=new Random();
        for(int i=0;i<54;i++){
            lis[i]=i;
        }
        for(int i=0;i<54;i++){
            int loc=random.nextInt(54);
            int t=lis[i];
            lis[i]=lis[loc];
            lis[loc]=t;

        }
        for(int i=0;i<54;i++){
           // System.out.println(lis[i]);
        }
    }
    Card[][] mycard=new Card[3][20];  //0自己 1左 2右
    Card[] lordcard=new Card[3];
    public void printcard(){
       // container = this.getContentPane();
        JLabel p2=new JLabel();
        p2.setLayout(new GridLayout(1,17,5,5));
        //System.out.println(lis[0]);
        Arrays.sort(lis,0,17);
        Arrays.sort(lis,17,34);
        Arrays.sort(lis,34,51);
        for(int i=0;i<17;i++){
            int num=(lis[i])/4+1;
            int type=(lis[i]+4)%4+1;
            num+=2;
            mycard[0][i]= new Card(this,num+"-"+type, true);
            mycard[0][i].setVisible(true);
            mycard[0][i].setBounds(420+i*45, 700, 71, 96);
            mycard[0][i].weight=num;
            mycard[0][i].type=type;
            //mycard[i].setLocation(100,100+i*10);
            //f.add(mycard[i]);
            container.add(mycard[0][i]);
            //container.setComponentZOrder(mycard[0][i],20-i);
           // p2.add(mycard[i]);
        }
        for(int i=33;i>=17;i--){
            int num=(lis[i])/4+1;
            int type=(lis[i]+4)%4+1;
            num+=2;

            mycard[1][33-i]= new Card(this,num+"-"+type, true);
            mycard[1][33-i].setVisible(true);
            mycard[1][33-i].setBounds(120, 900-i*25, 71, 96);
            mycard[1][33-i].weight=num;
            mycard[1][33-i].type=type;
            mycard[1][33-i].turnRear();
            mycard[1][33-i].canClick=false;
            //mycard[i].setLocation(100,100+i*10);
            //f.add(mycard[i]);
            container.add(mycard[1][33-i]);

        }
        for(int i=50;i>=34;i--){
            int num=(lis[i])/4+1;
            int type=(lis[i]+4)%4+1;
            num+=2;
            mycard[2][50-i]= new Card(this,num+"-"+type, true);
            mycard[2][50-i].setVisible(true);
            mycard[2][50-i].setBounds(1600, 1325-i*25, 71, 96);
            mycard[2][50-i].weight=num;
            mycard[2][50-i].type=type;
            mycard[2][50-i].turnRear();
            mycard[2][50-i].canClick=false;
            //mycard[2][50-i].turnRear();
            //mycard[i].setLocation(100,100+i*10);
            //f.add(mycard[i]);
            container.add(mycard[2][50-i]);
        }
        for(int i=51;i<=53;i++){
            int num=(lis[i])/4+1;
            int type=(lis[i]+4)%4+1;
            num+=2;
            lordcard[i-51]= new Card(this,num+"-"+type, false);
            lordcard[i-51].setVisible(true);
            lordcard[i-51].setBounds(700+(i-51)*71, 100, 71, 96);
            lordcard[i-51].weight=num;
            lordcard[i-51].type=type;
            //mycard[i].setLocation(100,100+i*10);
            //f.add(mycard[i]);
            container.add(lordcard[i-51]);
        }
       // getlord(1);
        Dimension dim = new Dimension();
        dim.setSize(1800,900 );
         f.setSize(dim);
        //设置窗体的坐标
        Point point = new Point(100, 100); // 设置坐标
        f.setLocation(point);
        this.repaint();
        while(t.lasttime>=0){
            t.time3();
            //lasttime.setText("TLE");
        }
       // f.add(p2,BorderLayout.SOUTH);
    }
    public  void findlord(int numb){
        getlord(numb);
        initial_mark=true;
        chupai.setVisible(true);
    }
    public void getlord(int x){
        loadnum=x;
        nowplay=x;
        lastchupai=x;
        //System.out.println(x);
        for(int i=0;i<3;i++){
            mycard[loadnum][17+i]=lordcard[i];
            if(loadnum==0)
            mycard[loadnum][17+i].turnFront();
            /*if(loadnum==0)
                mycard[loadnum][17+i].turnFront();
            else
                mycard[loadnum][17+i].turnRear();*/
            Card inicard=new Card(this,lordcard[i].weight+"-"+lordcard[i].type,true);
            inicard.setVisible(true);
            inicard.canClick=false;
            inicard.setBounds(700+i*71, 100, 71, 96);
            if(loadnum==0)
                mycard[loadnum][17+i].setBounds(420+(i+17)*45,700,71,96);
            else if(loadnum==1){
                mycard[loadnum][17+i].setBounds(120,75+(17+i)*25,71,96);

            }
            else{
                mycard[loadnum][17+i].setBounds(1600,75+(17+i)*25,71,96);

            }
            container.add(inicard);
        }
        sortit(mycard[loadnum]);
        repaint();
        if(loadnum==0)
        for(int i=0;i<20;i++){
            for(int j=0;j<3;j++){
                if(mycard[loadnum][i].weight==lordcard[j].weight && mycard[loadnum][i].type==lordcard[j].type){
                    mycard[loadnum][i].clicked=true;
                    Point from=mycard[loadnum][i].getLocation();
                    common.move(mycard[loadnum][i],from,new Point(from.x,from.y-20));
                }
            }
        }

    }
    public void repaint(){
        int cnt=0,cnt2=0;
        int r=17;
        if(loadnum==0) r+=3;
        for(int i=0;i<r;i++){
            if(mycard[0][i].canClick==false){
                mycard[0][i].setVisible(false);
            }
        }
        for(int i=0;i<r;i++){
            if(mycard[0][i].canClick==false) continue;
            if(mycard[0][i].clicked==false){

                Point from=mycard[0][i].getLocation();
                common.move(mycard[0][i],from,new Point(420+cnt*45,from.y));
                //container.setComponentZOrder(mycard[])
                cnt++;
            }
            else{

                Point from=mycard[0][i].getLocation();
                common.move(mycard[0][i],from,new Point(620+cnt2*45,from.y-200));
                mycard[0][i].canClick=false;
                cnt2++;
                //System.out.println(cnt2);
            }
            container.setComponentZOrder(mycard[0][i],20-cnt);
        }
        for(int k=1;k<=2;k++) {
            cnt=0;
            for (int i = 0; i < 20; i++) {
                if (i >= 17) {
                    if(loadnum!=k) continue;
                }
                if(mycard[k][i].canUse){
                    if(k==1){
                        Point from=mycard[k][i].getLocation();
                        //mycard[1][33-i].setBounds(120, 900-i*25, 71, 96);
                        mycard[k][i].setLocation(120,75+cnt*25);
                        container.setComponentZOrder(mycard[k][i],20-cnt);
                        //common.move(mycard[k][i],from,new Point(from.x,75+cnt*25));
                        cnt++;

                    }
                    else{
                        Point from=mycard[k][i].getLocation();
                        //mycard[1][33-i].setBounds(120, 900-i*25, 71, 96);
                        common.move(mycard[k][i],from,new Point(from.x,75+cnt*25));
                        container.setComponentZOrder(mycard[k][i],20-cnt);
                        cnt++;

                    }
                }
                else{
                    mycard[k][i].setVisible(false);
                }

            }
        }

        /*for(int i=0;i<13;i++){
            if(mycard[i].clicked==true){
                mycard[i].canClick=false;
                mycard[i].setVisible(false);
            }

        }*/
        //f.add(p2,BorderLayout.SOUTH);
        f.setVisible(true);

    }
    public void getcardback(){
        int r;
        if(loadnum==0) r=20;
        else r=17;
        for(int i=0;i<r;i++){
            if(mycard[0][i].canClick==true && mycard[0][i].clicked==true){
                Point from=mycard[0][i].getLocation();
                common.move(mycard[0][i],from,new Point(from.x,from.y+20));
                mycard[0][i].clicked=false;

            }
        }
    }
    static boolean cmp(Card x,Card y){
        if(x.weight==y.weight){
            if(x.type<y.type) return false;
            return  true;
        }
        if(x.weight<y.weight) return false;
        return true;
    }
    public static void sortit(Card[] cards){
        for(int i=0;i<=19;i++){
            for(int j=0;j<=19;j++){
                if(!cmp(cards[i],cards[j])){
                    Card t=cards[i];
                    cards[i]=cards[j];
                    cards[j]=t;
                }
            }
        }

    }
    public void Aicardreset(int numb){
        int r=17,cnt=0;
        if(loadnum==numb) r+=3;
        for(int i=0;i<r;i++) {
            if (mycard[numb][i].canUse) {
                if (numb == 1) {
                    Point from = mycard[numb][i].getLocation();
                    //mycard[1][33-i].setBounds(120, 900-i*25, 71, 96);
                    mycard[numb][i].setLocation(120, 75 + cnt * 25);
                    container.setComponentZOrder(mycard[numb][i], 20 - cnt);
                    //common.move(mycard[k][i],from,new Point(from.x,75+cnt*25));
                    cnt++;

                } else {
                    Point from = mycard[numb][i].getLocation();
                    //mycard[1][33-i].setBounds(120, 900-i*25, 71, 96);
                    common.move(mycard[numb][i], from, new Point(from.x, 75 + cnt * 25));
                    container.setComponentZOrder(mycard[numb][i], 20 - cnt);
                    cnt++;

                }
            }
        }
    }

}
