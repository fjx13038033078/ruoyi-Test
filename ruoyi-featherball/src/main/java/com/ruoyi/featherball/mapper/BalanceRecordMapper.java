package com.ruoyi.featherball.mapper;

import com.ruoyi.featherball.domain.BalanceRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单记录Mapper接口
 *
 * @Author fanjaixing
 * @Date 2024/3/9 12:05
 */
@Mapper
public interface BalanceRecordMapper {

    /**
     * 查询所有订单记录
     *
     * @return 订单记录列表
     */
    List<BalanceRecord> getAllBalanceRecords();

    /**
     * 根据主键查询订单记录
     *
     * @param recordId 主键
     * @return 订单记录
     */
    BalanceRecord getBalanceRecordById(Long recordId);

    /**
     * 添加订单记录
     *
     * @param balanceRecord 待添加的订单记录
     * @return 添加成功返回影响的行数，否则返回0
     */
    int addBalanceRecord(BalanceRecord balanceRecord);
}
