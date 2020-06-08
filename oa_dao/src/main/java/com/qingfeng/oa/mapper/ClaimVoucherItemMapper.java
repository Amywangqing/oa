package com.qingfeng.oa.mapper;

import com.qingfeng.oa.pojo.ClaimVoucherItem;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("claimVoucherItemMapper")
public interface ClaimVoucherItemMapper {
    void insert(ClaimVoucherItem claimVoucherItem);
    void update(ClaimVoucherItem claimVoucherItem);
    void delete(int id);

    /**
     * 根据报销单id查询报销单详情
     * @param cvid
     * @return
     */
    List<ClaimVoucherItem> selectByClaimVoucher(int cvid);
}
