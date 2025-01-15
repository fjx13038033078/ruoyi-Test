package com.ruoyi.study.service;

import com.ruoyi.study.domain.StudySeat;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/1/15 17:22
 */
public interface StudySeatService {
    /**
     * 获取所有座位信息
     * @return 所有座位列表
     */
    List<StudySeat> getAllSeats();

    /**
     * 根据座位ID获取座位信息
     * @param seatId 座位ID
     * @return 座位信息
     */
    StudySeat getSeatById(Long seatId);

    /**
     * 根据自习室ID获取所有座位
     * @param roomId 自习室ID
     * @return 自习室内的所有座位
     */
    List<StudySeat> getSeatsByRoomId(Long roomId);

    /**
     * 添加座位信息
     * @param studySeat 待添加的座位信息
     * @return 添加成功返回 true，否则返回 false
     */
    boolean addSeat(StudySeat studySeat);

    /**
     * 更新座位信息
     * @param studySeat 待更新的座位信息
     * @return 更新成功返回 true，否则返回 false
     */
    boolean updateSeat(StudySeat studySeat);

    /**
     * 删除座位信息
     * @param seatId 待删除的座位ID
     * @return 删除成功返回 true，否则返回 false
     */
    boolean deleteSeat(Long seatId);
}
