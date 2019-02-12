package com.zler.service;

import com.zler.domain.Product;

import java.util.List;

public interface ProdService {
    /**
     * 添加商品
     * @param prod 商品信息bean
     */
    void addProd(Product prod);

    /**
     * 查询所有商品
     * @return
     */
    List<Product> findAllProd();

    /**
     * 根据id查找商品
     * @param id
     * @return
     */
    Product findProdById(String id);
}
