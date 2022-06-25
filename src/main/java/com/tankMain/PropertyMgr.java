package com.tankMain;

import java.util.Properties;

//配置文件管理类
//单例模式
public class PropertyMgr {
    private static volatile PropertyMgr propsSingleton;
    static Properties props = new Properties();

    private PropertyMgr() {}

    public static synchronized PropertyMgr getInstance() {
        if (null == propsSingleton) {
            propsSingleton = new PropertyMgr();
        }
        return propsSingleton;
    }

    public static Object get(String key) {
        if(null == propsSingleton){
            PropertyMgr.getInstance();
        }
        try {
            propsSingleton.props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config/config"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null == propsSingleton.props) {
            return null;
        }
        return propsSingleton.props.get(key);
    }


    public static void main(String[] arg) {
        try {
            for (int i = 0; i < 100; i++) {
                new Thread(() -> System.out.println(PropertyMgr.getInstance().hashCode())).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
