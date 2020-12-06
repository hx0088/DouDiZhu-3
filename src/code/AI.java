package code;

import java.awt.*;
import java.util.ArrayList;

public class AI {
    public static void putleasetcard(main primer,int player){
        int r=17;
        if(primer.loadnum==player) r+=3;
        for(int i=0;i<r;i++){
            if(primer.mycard[player][i].canUse){
                primer.mycard[player][i].canUse=false;
                Point from=primer.mycard[player][i].getLocation();
                common.move(primer.mycard[player][i],from,new Point(300,300));
                primer.mycard[player][i].turnFront();
                primer.LastCard.len=1;
                primer.LastCard.lessw=primer.mycard[player][i].weight;
                primer.LastCard.type=cardtype.sola;
                break;
            }
        }
    }
    public static void checkputcard(main primer,int player){
        int cardnums=0;
        int []num=new int[18];
        ArrayList<Integer> q=new ArrayList<>();
        int r=17;
        if(primer.loadnum==player) r+=3;
        for(int i=0;i<r;i++){
            if(primer.mycard[player][i].canUse){
                cardnums++;
                if(primer.mycard[player][i].weight==16 && primer.mycard[player][i].type==2){
                    num[17]++;
                }
                else num[primer.mycard[player][i].weight]++;
            }
        }
        if(player!=primer.loadnum && primer.lastchupai!=primer.loadnum) return;
        if(primer.LastCard.type==cardtype.boom || primer.LastCard.type==cardtype.missle) return;
        if(primer.LastCard.type==cardtype.sola){
            for(int i=primer.LastCard.lessw+1;i<=17;i++){
                if(num[i]>0){
                    q.add(i);
                    AIputcard(primer,player,q);
                    primer.LastCard.len=1;
                    primer.LastCard.lessw=i;
                    primer.LastCard.type=cardtype.sola;
                    primer.lastchupai=player;
                    break;
                }
            }
            return;
        }
        if(primer.LastCard.type==cardtype.pair){
            for(int i=primer.LastCard.lessw+1;i<=17;i++){
                if(num[i]>=2){
                    q.add(i);
                    q.add(i);
                    AIputcard(primer,player,q);
                    primer.LastCard.len=2;
                    primer.LastCard.lessw=i;
                    primer.LastCard.type=cardtype.pair;
                    primer.lastchupai=player;
                    break;
                }
            }
            return;

        }
        if(primer.LastCard.type==cardtype.triple){
            for(int i=primer.LastCard.lessw+1;i<=17;i++){
                if(num[i]>=3){
                    q.add(i);
                    q.add(i);
                    q.add(i);
                    AIputcard(primer,player,q);
                    primer.LastCard.len=3;
                    primer.LastCard.lessw=i;
                    primer.LastCard.type=cardtype.triple;
                    primer.lastchupai=player;
                    break;
                }
            }
            return;

        }
        if(primer.LastCard.type==cardtype.triple_one){
            for(int i=primer.LastCard.lessw+1;i<=17;i++){
                if(num[i]>=3){
                    q.add(i);
                    q.add(i);
                    q.add(i);
                    for(int j=3;j<=17;j++){
                        if(j!=i && num[j]>0){
                            q.add(j);
                            AIputcard(primer,player,q);
                            primer.LastCard.len=4;
                            primer.LastCard.lessw=i;
                            primer.LastCard.type=cardtype.triple_one;
                            primer.lastchupai=player;
                            return;
                        }

                    }
                    q.clear();
                }
            }

        }
        if(primer.LastCard.type==cardtype.triple_two){
            for(int i=primer.LastCard.lessw+1;i<=17;i++){
                if(num[i]>=3){
                    q.add(i);
                    q.add(i);
                    q.add(i);
                    for(int j=3;j<=17;j++){
                        if(j!=i && num[j]>=2){
                            q.add(j);
                            q.add(j);
                            AIputcard(primer,player,q);
                            primer.LastCard.len=5;
                            primer.LastCard.lessw=i;
                            primer.LastCard.type=cardtype.triple_two;
                            primer.lastchupai=player;
                            return;
                        }

                    }
                    q.clear();
                }
            }

        }
        if(primer.LastCard.type==cardtype.flush){
            for(int i=primer.LastCard.lessw+1;i+primer.LastCard.len-1<=14;i++){
                for(int j=i;j<=i+primer.LastCard.len-1;j++){
                    if(num[j]==0) break;
                    if(j==i+primer.LastCard.len-1){
                        for(int k=i;k<=i+primer.LastCard.len-1;k++){
                            q.add(k);
                        }
                        AIputcard(primer,player,q);
                        primer.LastCard.lessw=i;
                        primer.lastchupai=player;
                        return;
                    }
                }
            }
        }
        if(primer.LastCard.type==cardtype.flushoftwo){
            for(int i=primer.LastCard.lessw+1;i+(primer.LastCard.len/2)-1<=14;i++){
                for(int j=i;j<=i+(primer.LastCard.len/2)-1;j++){
                    if(num[j]<=1) break;
                    if(j==i+(primer.LastCard.len/2)-1){
                        System.out.println("xx");
                        for(int k=i;k<=i+(primer.LastCard.len/2)-1;k++){
                            q.add(k);
                            q.add(k);
                        }
                        AIputcard(primer,player,q);
                        primer.LastCard.lessw=i;
                        primer.lastchupai=player;
                        return;
                    }
                }
            }
        }
        cardtype type=primer.LastCard.type;
        if(type==cardtype.boom_one || type==cardtype.boom_two || type==cardtype.plane || type==cardtype.plane_one || type==cardtype.plane_two){
            for(int i=3;i<=15;i++){
                if(num[i]==4){
                    for(int j=1;j<=4;j++) q.add(i);
                    AIputcard(primer,player,q);
                    primer.LastCard.lessw=i;
                    primer.LastCard.len=4;
                    primer.LastCard.type=cardtype.boom;
                    primer.lastchupai=player;
                    return;
                }
            }
        }
        return;
    }

    public static void AIputcard(main primer,int player,ArrayList<Integer>q) {
        int num=0;
        int r=17;if(primer.loadnum==player) r+=3;
        while(!q.isEmpty()){
            int nowcardnum=q.get(0);
            q.remove(0);
            for(int i=0;i<r;i++){
                if(!primer.mycard[player][i].canUse) continue;
                if(primer.mycard[player][i].weight==nowcardnum ||(primer.mycard[player][i].weight==16 && primer.mycard[player][i].type==2 && nowcardnum==17)){
                    primer.mycard[player][i].canUse=false;
                    Point from=primer.mycard[player][i].getLocation();
                    common.move(primer.mycard[player][i],from,new Point(300+num*30,300));
                    num++;
                    primer.container.setComponentZOrder(primer.mycard[player][i],num);
                    primer.mycard[player][i].turnFront();
                    break;

                }
            }
        }
        primer.Aicardreset(player);

    }
}
