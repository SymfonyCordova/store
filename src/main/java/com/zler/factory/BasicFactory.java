package com.zler.factory;

import java.io.FileReader;
import java.util.Properties;

public class BasicFactory {
    private static BasicFactory factory = new BasicFactory();
    private static Properties prop = null;

    static {
        try {
            prop = new Properties();
            prop.load(new FileReader(BasicFactory.class.getClassLoader().getResource("config.properties").getPath()));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private BasicFactory(){}

    public static BasicFactory getFacroty(){
        return factory;
    }

    public <T> T  getInstance(Class<T> clazz){
        try{
            String infaceName = clazz.getSimpleName();
            String implName = prop.getProperty(infaceName);
            return (T)Class.forName(implName).newInstance();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
