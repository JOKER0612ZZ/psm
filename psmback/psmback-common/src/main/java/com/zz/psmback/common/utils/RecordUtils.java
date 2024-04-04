package com.zz.psmback.common.utils;

import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
public class RecordUtils<T> {
    private final int maxSize = 10;
    //通过LinkedList来保存数据
    private LinkedList<T> list = new LinkedList<>();
    public void add(T t){
        if (list.contains(t)) {
            list.remove(t);
        }
        list.add(t); // 直接添加新元素到队尾
        if (list.size() > maxSize) {
            list.removeFirst();
        }
    }
    public boolean contains(T t){
        return list.contains(t);
    }
    public LinkedList<T> getList(){
        return list;
    }
    public void clear(){
        list.clear();
    }
    public int size(){
        return list.size();
    }
    public void setList(LinkedList<T> list){
        this.list = list;
    }
    @Override
    public String toString() {
        return "FixedLengthQueue {list=" + list + ", maxSize=" + maxSize + "}";
    }

}
