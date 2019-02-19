package com.zler.factory;

import com.zler.dao.Dao;
import com.zler.service.Service;
import com.zler.tool.TransactionManager;

import java.io.FileReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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

    public <T extends Dao> T  getDao(Class<T> clazz){
        try{
            String infaceName = clazz.getSimpleName();
            String implName = prop.getProperty(infaceName);
            return (T)Class.forName(implName).newInstance();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public <T extends Service> T  getService(Class<T> clazz){
        try{
            String infaceName = clazz.getSimpleName();
            String implName = prop.getProperty(infaceName);
            final T service = (T)Class.forName(implName).newInstance();

            T proxyService = (T) Proxy.newProxyInstance(
                    service.getClass().getClassLoader(),
                    service.getClass().getInterfaces(),
                    new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    try {
                        TransactionManager.startTran();
                        Object obj = method.invoke(service, args);
                        TransactionManager.commit();
                        return obj;
                    }catch (InvocationTargetException e) {
                        TransactionManager.rollback();
                        throw new RuntimeException(e.getTargetException());
                    } catch (Exception e) {
                        TransactionManager.rollback();
                        throw new RuntimeException(e);
                    }finally {
                        TransactionManager.release();
                    }
                }
            });

            return proxyService;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
