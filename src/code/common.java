package code;
import java.awt.Point;
import java.util.ArrayList;

public class common {
    public static cardtype Judgetype(ArrayList<Integer> a){
        int len=a.size();
        if(len==2 && a.get(0)==16 && a.get(1)==17){
            return cardtype.missle;
        }
        //System.out.println(len);
        boolean jokemark=false;
        int []num=new int[5];
        for(int i=1;i<=4;i++){
            num[i]=0;
        }
        int last=a.get(0),lastnum=1;
        for(int i=1;i<=len;i++){
            int noww;
            if(i==len){
                noww=0;
            }
            else{
                noww=a.get(i);
            }
            if(noww==16) jokemark=true;
            if(noww!=last){
                num[lastnum]++;
                lastnum=1;
                last=noww;

            }
            else{
                lastnum++;
            }
        }
       /* System.out.println(len);
        for (Integer integer : a) {
            System.out.println(integer);
        }*/
        if(len<=4){
                if(len==1)
                    return cardtype.sola;
                else if(len==2) {
                    if (a.get(0) == 16 && a.get(1) == 16) return cardtype.missle;
                    if (a.get(0) .equals( a.get(1))) return cardtype.pair;
                }
                else if(len==3){
                    if(a.get(0).equals(a.get(2))) return cardtype.triple;
                }
                else if(len==4){
                    if(a.get(0).equals(a.get(3))) return cardtype.boom;
                    if(a.get(0).equals(a.get(1)) && a.get(1).equals(a.get(2))) return cardtype.triple_one;
                    if(a.get(1).equals(a.get(2)) && a.get(2).equals(a.get(3))) return cardtype.triple_one;
                }
        }
        else{
            //判断三带二
            if(len==5){

                boolean tripe_two_mark=true;
                int []splist=new int[5];
                for(int i=0;i<=4;i++){
                    splist[i]=a.get(i);
                    if(splist[i]==16) tripe_two_mark=false;
                }

                if(tripe_two_mark==true){
                    if(splist[0]==splist[1] && splist[1]==splist[2] && splist[3]==splist[4]) return cardtype.triple_two;
                    if(splist[2]==splist[3] && splist[3]==splist[4] && splist[0]==splist[1]) return cardtype.triple_two;
                }
            }
            //判断顺子
            boolean flushmark=true;
            for(int i=1;i<len;i++){
                if(a.get(i).equals(16) || a.get(i).equals(15)){
                    flushmark=false;
                    break;
                }
                if(a.get(i-1)!=a.get(i)-1){
                    flushmark=false;
                    break;
                }
            }
            if(flushmark){
                return cardtype.flush;
            }
            // 判断连对
            if(len%2==0){
                boolean flush_two_mark=true;
                if(!a.get(0).equals(a.get(1))) flush_two_mark=false;
                for(int i=2;i<len-1 && flush_two_mark;i+=2){
                    if(!a.get(i).equals(a.get(i+1))) flush_two_mark=false;
                    if(a.get(i)!=a.get(i-2)+1) flush_two_mark=false;
                }
                if(flush_two_mark) return cardtype.flushoftwo;
            }
            // 判断单飞机
            if(len%3==0){
                boolean planemark=true;
                for(int i=0;i<len;i+=3){
                    if(!a.get(i).equals(a.get(i+2))){
                        planemark=false;
                        break;
                    }
                }
                if (planemark) return cardtype.plane;
            }
            //判断飞机带单张
            if(len%4==0){
                int swings=len/4;
                boolean flag=false;
                for(int i=0;i<=swings;i++){
                    for(int j=1;j<=swings-1;j++){
                        if(a.get(i)+j!=a.get(i+j*3)) break;
                        if(j==swings-1) {
                            if(a.get(i+j*3)==a.get(i+j*3+2))
                            flag=true;
                            break;
                        }
                    }
                }
                if(flag && num[3]==len/4 && num[1]+num[2]*2==len/4) return cardtype.plane_one;


            }
            // 判断飞机带对子
            if(len==10 && !jokemark){
                boolean flag=false;
                for(int i=2;i<=4;i++){
                    if(a.get(i)+1==a.get(i+3) && a.get(i+3).equals(a.get(i+5))) flag=true;
                }
                if(num[3]==len/5 && num[2]==num[3]) return cardtype.plane_two;
            }
            // 判断炸弹带单张
            if(len==6){
                if(num[4]==len/6 && num[1]+num[2]*2==len/3) return  cardtype.boom_one;
            }
            //判断炸弹带对子
            if(len==8){
                if(num[4]==len/8 && num[2]==len/4 && num[1]==0 && num[3]==0 ) return cardtype.boom_two;
                if(num[4]==len/4) return cardtype.plane_two;
            }
        }


        return cardtype.illegal;
    }

    public static int Judgelessw(ArrayList<Integer> a,cardtype type){
        if(a.size()<=3){
            return a.get(0);
        }
        if(type==cardtype.boom || type==cardtype.flush || type==cardtype.flushoftwo ){
            return a.get(0);
        }
        int num[]=new int[20];
        for(int i=3;i<=16;i++){
            num[i]=0;
        }
        for(int i=0;i<a.size();i++){
            num[a.get(i)]++;
        }
        if(type==cardtype.triple_one || type==cardtype.triple_two || type==cardtype.plane || type==cardtype.plane_one || type==cardtype.plane_two){
            for(int i=3;i<16;i++){
                if(num[i]==3) return i;
            }
        }
        if(type==cardtype.boom_one || type==cardtype.boom_two){
            for(int i=3;i<16;i++){
                if(num[i]==4) return i;
            }
        }

        return 0;
    }
    public static void move(Card card, Point from, Point to) {
        if (to.x != from.x) {
            double k = (1.0) * (to.y - from.y) / (to.x - from.x);
            double b = to.y - to.x * k;
            int flag = 0;// 判断向左还是向右移动步幅
            if (from.x < to.x)
                flag = 20;
            else {
                flag = -20;
            }
            for (int i = from.x; Math.abs(i - to.x) > 20; i += flag) {
                double y = k * i + b;// 这里主要用的数学中的线性函数

                card.setLocation(i, (int) y);
                /*try {
                    Thread.sleep(5); // 延迟，可自己设置
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        }
        // 位置校准
        card.setLocation(to);
    }
}
