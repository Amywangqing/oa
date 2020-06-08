package com.qingfeng.oa.service.impl;

import com.qingfeng.oa.mapper.EmployeeMapper;
import com.qingfeng.oa.pojo.Employee;
import com.qingfeng.oa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public void add(Employee employee) {
        employee.setPassword("123456");
        employeeMapper.insert(employee);
    }

    @Override
    public void edit(Employee employee) {
        employeeMapper.update(employee);
    }

    @Override
    public void remove(String sn) {
        employeeMapper.delete(sn);
    }

    @Override
    public Employee get(String sn) {
        return employeeMapper.select(sn);
    }

    @Override
    public List<Employee> getAll() {
        return employeeMapper.selectAll();
    }
}
