package com.qingfeng.oa.service;

import com.qingfeng.oa.pojo.ClaimVoucher;
import com.qingfeng.oa.pojo.ClaimVoucherItem;
import com.qingfeng.oa.pojo.DealRecord;

import java.util.List;

public interface ClaimVoucherService {

    /**
     * 保存报销单
     * @param claimVoucher  报销单基本信息
     * @param claimVoucherItems   报销单条目信息
     */
    public void  sava(ClaimVoucher claimVoucher, List<ClaimVoucherItem> claimVoucherItems);

    /**
     * 根据报销单id查询报销单
     * @param id 报销单id
     * @return
     */
    public ClaimVoucher get(Integer id);

    /**
     * 根据报销单id查询报销单条目
     * @param cvid
     * @return
     */
    List<ClaimVoucherItem> getItem(Integer cvid);

    /**
     * 根据报销单id查询报销处理记录
     * @param cvid
     * @return
     */
    List<DealRecord> getRecord(Integer cvid);

    /**
     * 根据员工编号获得个人报销单
     * @param sn
     * @return
     */
    List<ClaimVoucher> getForSelf(String sn);

    /**
     * 根据员工编号获得个人待处理的报销单
     * @param sn
     * @return
     */
    List<ClaimVoucher> getForDeal(String sn);

    /**
     * 修改报销单
     * @param claimVoucher  报销单基本信息
     * @param claimVoucherItems   报销单条目信息
     */
    public void  update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> claimVoucherItems);

    /**
     * 报销单提交
     * @param id 报销单id
     */
    public void submit(Integer id);

    /**
     * 审核和打款一起在这个方法里
     * @param dealRecord
     */
    public  void deal(DealRecord dealRecord);

}
