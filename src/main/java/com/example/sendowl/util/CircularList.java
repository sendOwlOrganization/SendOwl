package com.example.sendowl.util;

import java.util.List;

// 매번 다른 DB에 쿼리를 요청하기 위함
public class CircularList<T> {
    private List<T> list;
    private Integer counter = 0;

    public CircularList(List<T> list){
        this.list = list;
    }
    public T getOne(){
        if(counter + 1 >= list.size()){
            counter = -1;
        }
        return list.get(++counter);
    }
}
