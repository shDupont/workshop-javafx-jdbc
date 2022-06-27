package com.example.workshopjavafxjdbc.dao;

import com.example.workshopjavafxjdbc.dao.impl.DepartmentDaoJDBC;
import com.example.workshopjavafxjdbc.dao.impl.SellerDaoJDBC;
import com.example.workshopjavafxjdbc.db.DB;


public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return (DepartmentDao) new DepartmentDaoJDBC(DB.getConnection());
	}
}
