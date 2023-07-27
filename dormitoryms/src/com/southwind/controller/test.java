package com.southwind.controller;

import net.sf.json.JSONArray;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class test {
    public static void main(String[] args) {
        HashMap<String, List> stringListHashMap = new HashMap<>();
        stringListHashMap.put("11", Arrays.asList(1,2,3));
        stringListHashMap.put("22",Arrays.asList(4,5,6));
        System.out.println(Arrays.asList(1,2,3));
        System.out.println(stringListHashMap);
        JSONArray jsonArray = JSONArray.fromObject(stringListHashMap);
        System.out.println(jsonArray.toString());
        List list = stringListHashMap.get("11");
        System.out.println(list.get(2));
        System.out.println(stringListHashMap.get("11"));
    }
}
