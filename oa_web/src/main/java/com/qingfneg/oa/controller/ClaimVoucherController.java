package com.qingfneg.oa.controller;

import com.qingfeng.oa.global.Contant;
import com.qingfeng.oa.pojo.DealRecord;
import com.qingfeng.oa.pojo.Employee;
import com.qingfeng.oa.service.ClaimVoucherService;
import com.qingfneg.oa.dto.ClaimVoucherInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/claim_voucher")
public class ClaimVoucherController {

    @Autowired
    private ClaimVoucherService claimVoucherService;

    /**
     * 跳转到添加报销单页面
     * @param map
     * @return
     */
    @RequestMapping("/to_add")
    public String taAdd(Map<String,Object> map){
        //放置常量报销单条目
        map.put("items",Contant.getItems());
        map.put("info",new ClaimVoucherInfo());
        return "claim_voucher_add";
    }

    /**
     * 添加报销单
     * @param session
     * @param info  封装的ClaimVoucherInfo类的数据
     * @return
     */
    @RequestMapping("/add")
    public String add(HttpSession session, ClaimVoucherInfo info){
        Employee employee = (Employee)session.getAttribute("employee");
        info.getClaimVoucher().setCreateSn(employee.getSn());
        claimVoucherService.sava(info.getClaimVoucher(),info.getItems());
        return "redirect:detail?id="+info.getClaimVoucher().getId();
    }

    /**
     * 显示报销单信息
     * @param id
     * @param map
     * @return
     */
    @RequestMapping("/detail")
    public String detail(int id, Map<String,Object> map){
        map.put("claimVoucher",claimVoucherService.get(id));
        map.put("items",claimVoucherService.getItem(id));
        map.put("records",claimVoucherService.getRecord(id));
        return "claim_voucher_detail";
    }

    /**
     * 跳转个人报销单页面
     * @param session
     * @param map
     * @return
     */
    @RequestMapping("/self")
    public String self(HttpSession session, Map<String,Object> map){
        Employee employee = (Employee)session.getAttribute("employee");
        map.put("list",claimVoucherService.getForSelf(employee.getSn()));
        return "claim_voucher_self";
    }

    /**
     * 跳转待处理报销单页面
     * @param session
     * @param map
     * @return
     */
    @RequestMapping("/deal")
    public String deal(HttpSession session, Map<String,Object> map){
        Employee employee = (Employee)session.getAttribute("employee");
        map.put("list",claimVoucherService.getForDeal(employee.getSn()));
        return "claim_voucher_deal";
    }

    /**
     * 跳转到报销单页面
     * @param id
     * @param map
     * @return
     */
    @RequestMapping("/to_update")
    public String toUpdate(int id,Map<String,Object> map){
        map.put("items", Contant.getItems());
        ClaimVoucherInfo info =new ClaimVoucherInfo();
        //获得基本信息
        info.setClaimVoucher(claimVoucherService.get(id));
        //获得条目信息
        info.setItems(claimVoucherService.getItem(id));
        map.put("info",info);
        return "claim_voucher_update";
    }

    /**
     * 报销单的更新
     * @param session
     * @param info
     * @return
     */
    @RequestMapping("/update")
    public String update(HttpSession session, ClaimVoucherInfo info){
        Employee employee = (Employee)session.getAttribute("employee");
        info.getClaimVoucher().setCreateSn(employee.getSn());
        claimVoucherService.update(info.getClaimVoucher(),info.getItems());
        return "redirect:deal";
    }

    /**
     * 报销单的提交
     * @param id
     * @return
     */
    @RequestMapping("/submit")
    public String submit(int id){
        claimVoucherService.submit(id);
        return "redirect:deal";
    }

    /**
     * 去处理页面
     * @param id
     * @param map
     * @return
     */
    @RequestMapping("/to_check")
    public String toCheck(int id,Map<String,Object> map){
        map.put("claimVoucher",claimVoucherService.get(id));
        map.put("items",claimVoucherService.getItem(id));
        map.put("records",claimVoucherService.getRecord(id));
        DealRecord dealRecord =new DealRecord();
        dealRecord.setClaimVoucherId(id);
        map.put("record",dealRecord);
        return "claim_voucher_check";
    }

    /**
     * 进行处理，这里审核和打款一起处理
     * @param session
     * @param dealRecord
     * @return
     */
    @RequestMapping("/check")
    public String check(HttpSession session, DealRecord dealRecord){
        Employee employee = (Employee)session.getAttribute("employee");
        dealRecord.setDealSn(employee.getSn());
        claimVoucherService.deal(dealRecord);
        return "redirect:deal";
    }

}
