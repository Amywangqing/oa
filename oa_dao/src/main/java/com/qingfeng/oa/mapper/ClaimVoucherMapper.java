package com.qingfeng.oa.mapper;


import com.qingfeng.oa.pojo.ClaimVoucher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("claimVoucherMapper")
public interface ClaimVoucherMapper {
    void insert(ClaimVoucher claimVoucher);
    void update(ClaimVoucher claimVoucher);
    void delete(int id);
    ClaimVoucher select(int id);

    /**
     * 根据创建人查询报销单
     * @param csn
     * @return
     */
    List<ClaimVoucher> selectByCreateSn(String csn);

    /**
     *  根据待处理人查询报销单
     * @param ndsn
     * @return
     */
    List<ClaimVoucher> selectByNextDealSn(String ndsn);
}
