package com.ruoyi.featherball.service.impl;

import com.ruoyi.featherball.domain.BalanceRecord;
import com.ruoyi.featherball.mapper.BalanceRecordMapper;
import com.ruoyi.featherball.service.BalanceRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单记录service接口实现类
 *
 * @Author fanjaixing
 * @Date 2024/3/9 12:11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceRecordServiceImpl implements BalanceRecordService {
    private final BalanceRecordMapper balanceRecordMapper;

    /**
     * 获取所有订单记录信息
     *
     * @return 所有订单记录信息列表
     */
    @Override
    public List<BalanceRecord> getAllBalanceRecords() {
        return balanceRecordMapper.getAllBalanceRecords();
    }

    /**
     * 根据记录ID获取订单记录信息
     *
     * @param recordId 记录ID
     * @return 订单记录信息
     */
    @Override
    public BalanceRecord getBalanceRecordById(Long recordId) {
        return balanceRecordMapper.getBalanceRecordById(recordId);
    }

    /**
     * 添加订单记录信息
     *
     * @param balanceRecord 待添加的订单记录信息
     * @return 添加成功返回 true，否则返回 false
     */
    @Override
    public boolean addBalanceRecord(BalanceRecord balanceRecord) {
        return balanceRecordMapper.addBalanceRecord(balanceRecord) > 0;
    }
}
