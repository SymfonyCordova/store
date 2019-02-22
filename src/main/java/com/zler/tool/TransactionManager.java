package com.zler.tool;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private TransactionManager(){}

    private static DataSource source = new ComboPooledDataSource();
    private static ThreadLocal<Boolean> flagLocal= new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };
    private static ThreadLocal<Connection> connLocal = new ThreadLocal<Connection>();
    private static ThreadLocal<Connection> realLocal = new ThreadLocal<Connection>();

    public static void startTran() throws SQLException {
        flagLocal.set(true);
        final Connection conn = source.getConnection();
        conn.setAutoCommit(false);
        realLocal.set(conn);

       Connection proxConn = (Connection) Proxy.newProxyInstance(conn.getClass().getClassLoader(), conn.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if("close".equals(method.getName())){
                    return null;
                }else{
                    return method.invoke(conn, args);
                }
            }
        });

        connLocal.set(proxConn);
    }

    public static void commit(){
        DbUtils.commitAndCloseQuietly(connLocal.get());
    }

    public static void rollback(){
        DbUtils.rollbackAndCloseQuietly(connLocal.get());
    }

    /**
     * 这个方法应该做到:
     *  如果没有开启事务,则返回普通的数据源
     *  如果开启过事务,则返回一个改造过getConnection方法的数据源,这个方法改造后每次都返回
     * @return
     */
    public static DataSource getSource() throws SQLException {
        if (flagLocal.get()) {

            return (DataSource) Proxy.newProxyInstance(source.getClass().getClassLoader(), source.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if("getConnection".equals(method.getName())){
                        return connLocal.get();
                    }else{
                        return method.invoke(source, args);
                    }
                }
            });
        } else {
            return source;
        }
    }

    public static void release(){
        DbUtils.closeQuietly(realLocal.get());
        realLocal.remove();
        connLocal.remove();
        flagLocal.remove();
    }
}
