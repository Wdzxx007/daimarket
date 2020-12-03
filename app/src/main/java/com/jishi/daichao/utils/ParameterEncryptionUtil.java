package com.jishi.daichao.utils;


import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 参数加密的工具类
 * Created by fangwei on 2017/10/10.
 */

public class ParameterEncryptionUtil {

    private static ParameterEncryptionUtil instance;

    private ParameterEncryptionUtil() {

    }

    public static ParameterEncryptionUtil getInstance() {
        if (null == instance) {
            instance = new ParameterEncryptionUtil();
        }
        return instance;
    }


    /**
     * 将穿入的集合转化为加密的body
     *
     * @param map 需要转化的参数集合
     * @return 加密过后的body对象
     */
    public RequestBody getRequestBody(Map map) {
        RequestBody body = null;
        Gson gson = new Gson();
        String json = gson.toJson(map);
        String encString = null;
        try {
            String str1 = "";
            int i = 1;
            int j = map.size();
            for (Object str : map.keySet()) {
                if (i == j) {
                    str1 += str + "=" + map.get(str);
                } else {
                    str1 += str + "=" + map.get(str) + "&";
                }
                i++;
            }

            body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), str1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }
    public Map<String, String> obj2Map(Object obj) {

        Map<String, String> map = new HashMap<>();
        // System.out.println(obj.getClass());
        // 获取f对象对应类中的所有属性域
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
//            varName = varName.toLowerCase();//将key置为小写，默认为对象的属性
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o = fields[i].get(obj);
                if (o != null)
                    map.put(varName, o.toString());
                // System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }

}
