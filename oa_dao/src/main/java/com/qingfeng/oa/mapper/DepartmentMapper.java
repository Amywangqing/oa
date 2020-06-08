package com.qingfeng.oa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qingfeng.oa.pojo.Department;
import com.qingfeng.oa.pojo.Employee;

@Repository("departmentMapper")
public interface DepartmentMapper {

	void insert(Department department);
    void update(Department department);
    void delete(String sn);
    Department select(String sn);
    List<Department> selectAll();
	
}
