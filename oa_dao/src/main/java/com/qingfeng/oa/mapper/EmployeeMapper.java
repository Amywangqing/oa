package com.qingfeng.oa.mapper;


import com.qingfeng.oa.pojo.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("employeeMapper")
public interface EmployeeMapper {
    void insert(Employee employee);
    void update(Employee employee);
    void delete(String sn);
    Employee select(String sn);
    List<Employee> selectAll();

    /**
     * 根据部门id和职位查询员工
     * @param dsn
     * @param post
     * @return
     */
    List<Employee> selectByDepartmentAndPost(@Param("dsn") String dsn, @Param("post") String post);
}
