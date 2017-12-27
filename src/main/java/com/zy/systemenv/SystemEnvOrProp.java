package com.zy.systemenv;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class SystemEnvOrProp {

    public static void main(String[] args) {
        System.out.println(System.getProperty("hadoop.master"));


        Map<String, String> envs = System.getenv();
        Set<Map.Entry<String, String>> set = envs.entrySet();
        set.stream().

                forEach(item ->

                {
                    System.out.println(item.getKey() + ":" + item.getValue());
                });

        System.out.println("=================other way ===================");
        Properties props = System.getProperties();
        Set<Map.Entry<Object, Object>> set1 = props.entrySet();
        /*stream foreach 用法，过滤用法*/
        set1.stream().

                filter(item -> item.getKey().

                        toString().

                        length() > 6).

                forEach(item -> System.out.println(item.getKey().

                        toString() + ":" + item.getValue().

                        toString()));
    }

}


