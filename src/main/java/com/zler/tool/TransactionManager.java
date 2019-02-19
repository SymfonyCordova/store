package com.zler.tool;

import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private TransactionManager(){}

    //private static Connection conn = JDBCToolkit.getConn();
    private static ThreadLocal<Connection> conn = new ThreadLocal<Connection>(){
        @Override
        protected Connection initialValue() {
            return JDBCToolkit.getConn();
        }
    };

    public static void startTran() throws SQLException {
        conn.get().setAutoCommit(false);
    }

    public static void commit(){
        DbUtils.commitAndCloseQuietly(conn.get());
    }

    public static void rollback(){
        DbUtils.rollbackAndCloseQuietly(conn.get());
    }

    public static Connection getConn(){
        return conn.get();
    }

    public static void release(){
        conn.remove();
    }
}
