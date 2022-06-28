package com.example.workshopjavafxjdbc.services;


import com.example.workshopjavafxjdbc.Seller;
import com.example.workshopjavafxjdbc.dao.DaoFactory;
import com.example.workshopjavafxjdbc.dao.SellerDao;

import java.util.List;

public class SellerService {
    private final SellerDao dao = DaoFactory.createSellerDao();
    public List<Seller> findAll(){

        return dao.findAll();
    }

    public void saveOrUpdate(Seller obj){
        if(obj.getId() == null){
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }
    public void remove(Seller obj){
        dao.deleteById(obj.getId());
    }
}
