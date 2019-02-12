package com.zler.dao;

import com.zler.domain.Product;

import java.util.List;

public interface ProdDao {
    /**
     * 添加商品
     * @param prod
     */
    void addProd(Product prod);

    /**
     * 查询所有商品
     * @return
     */
    List<Product> findAllProd();

    /**
     * 根据id查找商品信息
     * @param id
     * @return
     */
    Product findProdById(String id);
}
