package com.zler.tool;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class JDBCToolkit {
    private static DataSource source = new ComboPooledDataSource();

    private JDBCToolkit(){}

    public static DataSource getSource(){
        return source;
    }

    public static Connection getConn(){
        try {
            return source.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
