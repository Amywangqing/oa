package com.qingfneg.oa.controller;

import com.qingfeng.oa.global.Contant;
import com.qingfeng.oa.pojo.Department;
import com.qingfeng.oa.pojo.Employee;
import com.qingfeng.oa.service.DepartmentService;
import com.qingfeng.oa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/list")
    public String list(Map<String,Object> map){
        map.put("employee",employeeService.getAll());
        return "employee_list";
    }

    /**
     * 跳转到添加页面
     *
     * @param map
     * @return
     */
    @RequestMapping("/to_add")
    public String toAdd(Map<String, Object> map) {
        map.put("employee", new Employee());
        //显示部门信息
        map.put("departmentList",departmentService.getAll());
        //员工职位
        map.put("postList",Contant.getPosts());
        return "employee_add";

    }

    /**
     * 添加完成到重定向到list接口
     *
     * @param employee
     * @return
     */
    @RequestMapping("/add")
    public String add(Employee employee) {
        employeeService.add(employee);
        return "redirect:list";
    }

    @RequestMapping(value = "/to_update", params = "sn")
    public String toUpdate(String sn, Map<String, Object> map) {
        System.out.println("==============to_update============to_update======");
        map.put("employee", employeeService.get(sn));
        //显示部门信息
        map.put("departmentList",departmentService.getAll());
        //员工职位
        map.put("postList",Contant.getPosts());
        return "employee_update";
    }

    @RequestMapping("/update")
    public String update(Employee employee) {
        employeeService.edit(employee);
        return "redirect:list";
    }

    @RequestMapping(value = "/remove", params = "sn")
    public String remove(String sn) {
        employeeService.remove(sn);
        return "redirect:list";
    }

}
