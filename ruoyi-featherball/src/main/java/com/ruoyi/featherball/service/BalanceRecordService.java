package com.ruoyi.featherball.service;

import com.ruoyi.featherball.domain.BalanceRecord;

import java.util.List;

/**
 * 订单记录service接口
 *
 * @Author fanjaixing
 * @Date 2024/3/9 12:09
 */
public interface BalanceRecordService {

    /**
     * 获取所有订单记录
     * @return 所有订单记录列表
     */
    List<BalanceRecord> getAllBalanceRecords();

    /**
     * 根据主键获取订单记录
     * @param recordId 主键
     * @return 订单记录
     */
    BalanceRecord getBalanceRecordById(Long recordId);

    /**
     * 添加订单记录
     * @param balanceRecord 待添加的订单记录
     * @return 添加成功返回 true，否则返回 false
     */
    boolean addBalanceRecord(BalanceRecord balanceRecord);

}
