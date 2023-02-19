package com.venson.commonutils;

import java.util.Iterator;
import java.util.Set;

public class SetUtil {
    private SetUtil(){}
    public static <T> void xorSet(Set<T> a, Set<T> b){
        if(a.size()>b.size()){
            Set<T> temp;
            temp=b;
            b=a;
            a=temp;
        }
        for(Iterator<T> i =  a.iterator();i.hasNext();){
            T next = i.next();
            if(b.contains(next)){
                i.remove();
                b.remove(next);
            }
        }

    }
}
