package com.example.workshopjavafxjdbc.services;


import com.example.workshopjavafxjdbc.Department;
import com.example.workshopjavafxjdbc.dao.DaoFactory;
import com.example.workshopjavafxjdbc.dao.DepartmentDao;

import java.util.List;

public class DepartmentService {
    private final DepartmentDao dao = DaoFactory.createDepartmentDao();
    public List<Department> findAll(){

        return dao.findAll();
    }
}
