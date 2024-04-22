package com.zz.psmback.common.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
@Data
public class RecordUtils<T> {
    private int maxSize;

    public RecordUtils(){

    }
    public RecordUtils(int maxSize) {
        this.maxSize = maxSize;
    }

    //通过LinkedList来保存数据
    private LinkedList<T> list = new LinkedList<>();

    public boolean contains(T t){
        return list.stream().anyMatch(st->st.equals(t));
    }
    public void add(T t){
        if (contains(t)) {
            list.remove(t);
        }
        list.add(t); // 直接添加新元素到队尾
        if (list.size() > maxSize) {
            list.removeFirst();
        }
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
