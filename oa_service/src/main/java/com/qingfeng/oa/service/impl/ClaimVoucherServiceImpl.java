package com.qingfeng.oa.service.impl;

import com.qingfeng.oa.global.Contant;
import com.qingfeng.oa.mapper.ClaimVoucherItemMapper;
import com.qingfeng.oa.mapper.ClaimVoucherMapper;
import com.qingfeng.oa.mapper.DealRecordMapper;
import com.qingfeng.oa.mapper.EmployeeMapper;
import com.qingfeng.oa.pojo.ClaimVoucher;
import com.qingfeng.oa.pojo.ClaimVoucherItem;
import com.qingfeng.oa.pojo.DealRecord;
import com.qingfeng.oa.pojo.Employee;
import com.qingfeng.oa.service.ClaimVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("claimVoucherService")
public class ClaimVoucherServiceImpl implements ClaimVoucherService {

    @Autowired
    private ClaimVoucherMapper claimVoucherMapper;
    @Autowired
    private ClaimVoucherItemMapper claimVoucherItemMapper;
    @Autowired
    private DealRecordMapper dealRecordMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 保存报销单
     *
     * @param claimVoucher      报销单基本信息
     * @param claimVoucherItems 报销单条目信息
     */
    @Override
    public void sava(ClaimVoucher claimVoucher, List<ClaimVoucherItem> claimVoucherItems) {
        //设置时间
        claimVoucher.setCreateTime(new Date());
        //设置待处理人
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        //设置报销的状态,新创建
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        claimVoucherMapper.insert(claimVoucher);

        for (ClaimVoucherItem item : claimVoucherItems) {
            item.setClaimVoucherId(claimVoucher.getId());
            claimVoucherItemMapper.insert(item);
        }
    }

    /**
     * 根据报销单id查询报销单
     *
     * @param id 报销单id
     * @return
     */
    @Override
    public ClaimVoucher get(Integer id) {
        return claimVoucherMapper.select(id);
    }

    /**
     * 根据报销单id查询报销单条目
     *
     * @param cvid
     * @return
     */
    @Override
    public List<ClaimVoucherItem> getItem(Integer cvid) {
        return claimVoucherItemMapper.selectByClaimVoucher(cvid);
    }

    /**
     * 根据报销单id查询报销处理记录
     *
     * @param cvid
     * @return
     */
    @Override
    public List<DealRecord> getRecord(Integer cvid) {
        return dealRecordMapper.selectByClaimVoucher(cvid);
    }

    /**
     * 根据员工编号获得个人报销单
     *
     * @param sn
     * @return
     */
    @Override
    public List<ClaimVoucher> getForSelf(String sn) {
        return claimVoucherMapper.selectByCreateSn(sn);
    }

    /**
     * 根据员工编号获得个人待处理的报销单
     *
     * @param sn
     * @return
     */
    @Override
    public List<ClaimVoucher> getForDeal(String sn) {
        return claimVoucherMapper.selectByNextDealSn(sn);
    }

    /**
     * 修改报销单
     *
     * @param claimVoucher      报销单基本信息
     * @param claimVoucherItems 报销单条目信息
     */
    @Override
    public void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> claimVoucherItems) {
        //设置待处理人
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        claimVoucherMapper.update(claimVoucher);

        //获取当前数据库已经有的条目
        List<ClaimVoucherItem> oldClaimVoucherItems = claimVoucherItemMapper.selectByClaimVoucher(claimVoucher.getId());
        //遍历数据库已经有的条目
        for (ClaimVoucherItem oldItem : oldClaimVoucherItems) {
            boolean isHave = false;
            //遍历新的条目
            for (ClaimVoucherItem item : claimVoucherItems) {
                //新的条目和旧的条目的id相同
                if (item.getId() == oldItem.getId()) {
                    isHave = true;
                    break;
                }
            }
            if (!isHave) {
                //删除
                claimVoucherItemMapper.delete(oldItem.getId());
            }
        }
        //迭代新的条目
        for (ClaimVoucherItem item : claimVoucherItems) {
            //为条目设置id
            item.setClaimVoucherId(claimVoucher.getId());
            //拿出每一条的条目的id大于0，则数据库就有了条目，就更新里面的内容
            if (item.getId() != null && item.getId() > 0) {
                //更新条目
                claimVoucherItemMapper.update(item);
            } else {
                //增加条目
                claimVoucherItemMapper.insert(item);
            }
        }
    }

    /**
     * 报销单提交
     *
     * @param id 报销单id
     */
    @Override
    public void submit(Integer id) {
        //根据报销单id查询报销单
        ClaimVoucher claimVoucher = claimVoucherMapper.select(id);
        //根据
        Employee employee = employeeMapper.select(claimVoucher.getCreateSn());

        //报销单状态，已提交CLAIMVOUCHER_SUBMIT
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_SUBMIT);
        //待处理人
        claimVoucher.setNextDealSn(employeeMapper.selectByDepartmentAndPost(employee.getDepartmentSn(), Contant.POST_FM).get(0).getSn());
        //更新报销单
        claimVoucherMapper.update(claimVoucher);

        //进行报销的记录的保存
        DealRecord dealRecord = new DealRecord();
        //处理方式
        dealRecord.setDealWay(Contant.DEAL_SUBMIT);
        //处理人
        dealRecord.setDealSn(employee.getSn());
        //报销单编号
        dealRecord.setClaimVoucherId(id);
        //报销单的状态,提交状态
        dealRecord.setDealResult(Contant.CLAIMVOUCHER_SUBMIT);
        dealRecord.setDealTime(new Date());
        //备注
        dealRecord.setComment("无");
        //报销的记录的保存
        dealRecordMapper.insert(dealRecord);
    }

    @Override
    public void deal(DealRecord dealRecord) {
        ClaimVoucher claimVoucher = claimVoucherMapper.select(dealRecord.getClaimVoucherId());
        Employee employee = employeeMapper.select(dealRecord.getDealSn());
        //审核通过
        if (dealRecord.getDealWay().equals(Contant.DEAL_PASS)) {
            //金额小于5000或者审核的人的职位是总经理,就不需要复审
            if (claimVoucher.getTotalAmount() <= Contant.LIMIT_CHECK || employee.getPost().equals(Contant.POST_GM)) {
                //已经审核通过
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_APPROVED);
                //待处理人,财务
                claimVoucher.setNextDealSn(employeeMapper.selectByDepartmentAndPost(null, Contant.POST_CASHIER).get(0).getSn());

                //处理记录
                //报销单的状态
                dealRecord.setDealResult(Contant.CLAIMVOUCHER_APPROVED);
                dealRecord.setDealTime(new Date());
            } else {
                //需要复审
                //待复审
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_RECHECK);
                //需要复审,待处理人只有总经理
                claimVoucher.setNextDealSn(employeeMapper.selectByDepartmentAndPost(null, Contant.POST_GM).get(0).getSn());
                dealRecord.setDealTime(new Date());
                //需要复审报销单的状态
                dealRecord.setDealResult(Contant.CLAIMVOUCHER_APPROVED);

            }
        } else if (dealRecord.getDealWay().equals(Contant.DEAL_BACK)) {
            //审核不通过，打回
            //状态，以打回
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_BACK);
            //打回，待处理人就只有创建报销单的人
            claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
            dealRecord.setDealTime(new Date());
            //处理结果以打回
            dealRecord.setDealResult(Contant.CLAIMVOUCHER_BACK);
        } else if (dealRecord.getDealWay().equals(Contant.DEAL_PAID)) {
            //打款操作
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_PAID);
            claimVoucher.setNextDealSn(null);
            dealRecord.setDealTime(new Date());
            //处理结果以打款
            dealRecord.setDealResult(Contant.CLAIMVOUCHER_PAID);
        }

        //更新报销单
        claimVoucherMapper.update(claimVoucher);
        //保存处理记录
        dealRecordMapper.insert(dealRecord);

    }


}
