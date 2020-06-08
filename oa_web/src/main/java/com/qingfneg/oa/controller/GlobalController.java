package com.qingfneg.oa.controller;

import com.qingfeng.oa.pojo.Employee;
import com.qingfeng.oa.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class GlobalController {

    @Autowired
    private GlobalService globalService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    /**
     * 登录
     * @param sn
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public String login(@RequestParam String sn, @RequestParam String password, HttpSession session) {
        Employee employee = globalService.login(sn, password);
        if (employee == null) {
            return "redirect:to_login";
        }
        //登录成功把用户保存到session里
        session.setAttribute("employee", employee);
        return "self";
    }

    /**
     * 登录成功的界面
     *
     * @return
     */
    @RequestMapping("/self")
    public String self() {
        return "self";
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @RequestMapping("/quit")
    public String quit(HttpSession session) {
        session.setAttribute("employee",null);
        return "redirect:to_login";
    }


    /**
     * 跳转到修改密码页面
     * @return
     */
    @RequestMapping("/to_change_password")
    public String toChangePassword() {
        return "change_password";
    }

    /**
     * 修改密码
     * @param session
     * @param old 旧密码
     * @param new1  新密码
     * @param new2  确认密码
     * @return
     */
    @RequestMapping("/change_password")
    public String quit(HttpSession session,@RequestParam String old,@RequestParam String new1,@RequestParam String new2) {
        Employee employee = (Employee)session.getAttribute("employee");
        if (employee.getPassword().equals(old)){
            if (new1.equals(new2)){
                employee.setPassword(new1);
                globalService.changePassword(employee);
                return "redirect:self";
            }
        }
        return "redirect:to_change_password";
    }

}
