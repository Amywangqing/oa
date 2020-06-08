package com.qingfeng.oa.service.impl;

import com.qingfeng.oa.mapper.EmployeeMapper;
import com.qingfeng.oa.pojo.Employee;
import com.qingfeng.oa.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("globalService")
public class GlobalServiceImpl implements GlobalService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 登录
     * @param sn
     * @param password
     * @return
     */
    @Override
    public Employee login(String sn, String password) {
        Employee employee = employeeMapper.select(sn);
        if (employee.getPassword().equals(password) && employee != null){
            return employee;
        }
        return null;
    }

    /**
     * 修改密码
     * @param employee
     */
    @Override
    public void changePassword(Employee employee) {
        employeeMapper.update(employee);
    }
}
