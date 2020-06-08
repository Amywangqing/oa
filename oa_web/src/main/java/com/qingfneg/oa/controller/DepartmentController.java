package com.qingfneg.oa.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qingfeng.oa.pojo.Department;
import com.qingfeng.oa.service.DepartmentService;

@Controller("departmentController")
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 查看部门
     *
     * @param map
     * @return
     */
    @RequestMapping("/list")
    public String list(Map<String, Object> map) {
        System.out.println("=================================");
        map.put("list", departmentService.getAll());
        return "department_list";
    }

    /**
     * 跳转到添加页面
     *
     * @param map
     * @return
     */
    @RequestMapping("/to_add")
    public String toAdd(Map<String, Object> map) {
        map.put("department", new Department());
        return "department_add";

    }

    /**
     * 添加完成到重定向到list接口
     *
     * @param department
     * @return
     */
    @RequestMapping("/add")
    public String add(Department department) {
        departmentService.add(department);
        return "redirect:list";
    }

    /**
     * 跳转到修改页面
     * @param sn
     * @param map
     * @return
     */
    @RequestMapping(value = "/to_update", params = "sn")
    public String toUpdate(String sn, Map<String, Object> map) {
        System.out.println("==============to_update============to_update======");
        map.put("department", departmentService.get(sn));
        return "department_update";
    }

    /**
     *修改
     * @param department
     * @return
     */
    @RequestMapping("/update")
    public String update(Department department) {
        departmentService.edit(department);
        return "redirect:list";
    }

    /**
     * 删除
     * @param sn
     * @return
     */
    @RequestMapping(value = "/remove", params = "sn")
    public String remove(String sn) {
        departmentService.remove(sn);
        return "redirect:list";
    }
}
