package code;

public enum  cardtype {

    sola,//单张
    pair,//对子
    triple,//三张
    boom,//炸弹
    missle,//王炸
    triple_one,//三带一
    triple_two,//三带二
    flush,//顺子
    flushoftwo,//连对
    plane,//飞机
    plane_one,//飞机带单张
    plane_two,//飞机带对子
    boom_one,//炸弹带单
    boom_two,//炸弹带对子
    illegal; //非法组合
    public void get(){
        System.out.println(this.name());
    }
};

