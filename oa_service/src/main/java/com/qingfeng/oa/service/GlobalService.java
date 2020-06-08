package com.qingfeng.oa.service;

import com.qingfeng.oa.pojo.Employee;

public interface GlobalService {

    /**
     * 登录
     * @param sn
     * @param password
     * @return
     */
    Employee login(String sn,String password);

    /**
     * 修改密码
     * @param employee
     */
    void  changePassword(Employee employee);
}
