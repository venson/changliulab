package com.venson.commonutils;


import java.util.Collection;

public class EmptyUtils {
    public static <T>boolean hasContent(Collection<T> collection){
        return collection != null && collection.size() > 0;
    }
}
