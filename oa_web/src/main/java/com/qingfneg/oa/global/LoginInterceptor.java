package com.qingfneg.oa.global;

import com.qingfeng.oa.pojo.Employee;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取当前请求的路径
        String url = request.getRequestURI();
        //请求的路径含有login放行
        if(url.toLowerCase().indexOf("login") > 0){
            System.out.println("请求的路径含有login放行");
            return true;
        }
        HttpSession session = request.getSession();
        Employee employee = (Employee)session.getAttribute("employee");
        //用户不为空，就应经登录了，放行
        if (employee != null){
            System.out.println("用户不为空，就应经登录了，放行");
            return true;
        }
        //重定向到登录页
        response.sendRedirect("/to_login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
