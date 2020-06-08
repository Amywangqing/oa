package com.qingfeng.oa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qingfeng.oa.mapper.DepartmentMapper;
import com.qingfeng.oa.pojo.Department;
import com.qingfeng.oa.service.DepartmentService;
@Service
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentMapper DepartmentMapper;

	@Override
	public void add(Department department) {
		DepartmentMapper.insert(department);		
	}

	@Override
	public void edit(Department department) {
		DepartmentMapper.update(department);
		
	}

	@Override
	public void remove(String sn) {
		DepartmentMapper.delete(sn);
		
	}

	@Override
	public Department get(String sn) {
		
		return DepartmentMapper.select(sn);
	}

	@Override
	public List<Department> getAll() {
	
		return DepartmentMapper.selectAll();
	}
	
	
}
