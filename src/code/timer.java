package code;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.awt.Point;
import java.util.Vector;
import java.util.List;
public class timer extends Thread {
    main primer;
    boolean canrun=true;
    int lasttime=30;
    public timer(main m){
        this.primer=m;
    }

    public  void time3() {
        win();
        //System.out.println(primer.lastchupai);
        if(primer.initial_mark==true) {
            if (primer.nowplay == 1) {
                primer.chupai.setVisible(false);
                primer.notchupai.setVisible(false);
            }
            if (primer.nowplay == 0 ) {
                primer.chupai.setVisible(true);
                primer.notchupai.setVisible(true);

            }
        }
        //System.out.println(primer.LastCard.type+" "+primer.LastCard.lessw);
        if(primer.nowplay==primer.lastchupai){

            primer.LastCard.len=0;
            if(primer.nowplay!=0){
                second(1);
                AI.putleasetcard(primer,primer.nowplay);
                this.lasttime=30;
                primer.nowplay++;
                primer.nowplay%=3;

            }

        }
        else{
            if(primer.nowplay!=0){
                second(1);
                AI.checkputcard(primer,primer.nowplay);
                this.lasttime=30;
                primer.nowplay++;
                primer.nowplay%=3;

            }
        }
        Timer timers = new Timer();

        if(this.lasttime>0){
            if(primer.nowplay==0) {
                this.lasttime--;
                primer.lasttime.setText("倒计时：" + lasttime);
                second(1);
            }
            else{
                primer.lasttime.setText("WaitAI" );
            }
        }
        else{
            if(primer.lastchupai==primer.nowplay && primer.nowplay==0){
                int r=17;
                if(primer.loadnum==0) r+=3;
                primer.getcardback();
                for(int i=0;i<r;i++){
                    if(primer.mycard[0][i].canClick==true){
                        primer.mycard[0][i].clicked=true;
                        break;
                    }
                }
                primer.repaint();

            }
            this.lasttime=30;
            primer.nowplay++;
            primer.nowplay%=3;
        }


    }
    public void second(int i) {
        try {
            Thread.sleep(i * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void endseekload(main pr,int x){
        pr.initial_mark=true;
        this.lasttime=30;
        primer.notchupai.setVisible(true);
        primer.chupai.setVisible(true);
        primer.seeklord.setVisible(false);
        primer.notseeklord.setVisible(false);
    }
    public int getcardnum(main primer,int numb){
        int res=0;
        int r=17;
        if(primer.loadnum==numb) r+=3;
        for(int i=0;i<r;i++){
            if(numb==0){
                if(primer.mycard[numb][i].canClick==true) res++;
            }
            else{
                if(primer.mycard[numb][i].canUse) res++;
            }
        }
        return res;
    }
    public void win(){
        for(int i=0;i<3;i++){
            if(getcardnum(primer,i)==0){
                System.out.println("goodgame");
                System.exit(0);
            }
        }
    }
    public boolean countVal(int x){
        return true;
    }
}