package com.zler.service.impl;

import com.zler.dao.ProdDao;
import com.zler.domain.Product;
import com.zler.factory.BasicFactory;
import com.zler.service.ProdService;

import java.util.List;
import java.util.UUID;

public class ProdServiceImpl implements ProdService {
    ProdDao dao = BasicFactory.getFacroty().getInstance(ProdDao.class);

    @Override
    public void addProd(Product prod) {
        prod.setId(UUID.randomUUID().toString());
        dao.addProd(prod);
    }

    @Override
    public List<Product> findAllProd() {
        return dao.findAllProd();
    }

    @Override
    public Product findProdById(String id) {
        return dao.findProdById(id);
    }
}
