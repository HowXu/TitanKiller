package com.howxu.demo.Event;

import java.util.ArrayList;
import java.util.List;


//防缴械
public class AntiDisarm {

    private static final List<String> HasDemo = new ArrayList<String>();//创建一个数组存储玩家

    public static void Add(String name){
        HasDemo.add(name);//一个用来为HasDemo添加玩家的方法
    }
    public static boolean HasIt(String name){
        return HasDemo.contains(name);//一个用来判断玩家是否属于数组的方法
    }

}
