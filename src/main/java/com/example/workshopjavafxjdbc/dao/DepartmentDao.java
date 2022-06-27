package com.example.workshopjavafxjdbc.dao;

import java.util.List;

import com.example.workshopjavafxjdbc.Department;


public interface DepartmentDao {

	void insert(Department obj);
	void update(Department obj);
	void deleteById(Integer id);
	Department findById(Integer id);
	List<Department> findAll();
}
