package com.yc.mybank.bean;

public enum OpTypes {

    deposite("deposite",1),
    withdraw("withdraw",2),
    transfer("transfer",3);

    private String name;
    private int index;


    //构造方法要私有化
    //注意name
    private OpTypes(String name, int index) {
        this.name=name;
        this.index=index;
    }

    @Override
    public String toString() {
        return this.index+"_"+this.name;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}
