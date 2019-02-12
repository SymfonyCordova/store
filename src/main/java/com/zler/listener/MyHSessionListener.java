package com.zler.listener;

import com.zler.domain.Product;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.LinkedHashMap;

public class MyHSessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute("cartmap", new LinkedHashMap<Product, Integer>());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }
}
