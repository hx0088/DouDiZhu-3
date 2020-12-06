package doudizhu;

public class CardValue{
    cardtype type;
    int len;
    int lessw;
    public static int islarge(CardValue a,CardValue b){
        if(a.type==cardtype.missle) return -1;
        if(b.type==cardtype.missle) return 1;
        if(a.type==cardtype.boom && b.type!=cardtype.boom) return -1;
        if(a.type!=cardtype.boom && b.type==cardtype.boom) return 1;
        if(a.type!=b.type) return 0;
        if(a.type==cardtype.flush || a.type==cardtype.flushoftwo){
            if(a.len!=b.len) return 0;
        }
        if(a.lessw<b.lessw) return 1;
        return -1;
    }

}
