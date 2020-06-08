package com.qingfeng.oa.mapper;

import com.qingfeng.oa.pojo.DealRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("dealRecordMapper")
public interface DealRecordMapper {
    void insert(DealRecord dealRecord);
    /**
     * 根据报销单id查询处理记
     * @param cvid
     * @return
     */
    List<DealRecord> selectByClaimVoucher(int cvid);
}
