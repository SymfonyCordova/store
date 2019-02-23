package com.zler.domain;

import java.io.Serializable;

public class SaleInfo implements Serializable {
    private String id;
    private String name;
    private int salenum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalenum() {
        return salenum;
    }

    public void setSalenum(int salenum) {
        this.salenum = salenum;
    }
}
