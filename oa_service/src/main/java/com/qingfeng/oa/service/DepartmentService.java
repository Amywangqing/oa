package com.qingfeng.oa.service;


import java.util.List;

import com.qingfeng.oa.pojo.Department;

public interface DepartmentService {
    void add(Department department);
    void edit(Department department);
    void remove(String sn);
    Department get(String sn);
    List<Department> getAll();
}
