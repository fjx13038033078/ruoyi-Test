package com.ruoyi.study.service;

import com.ruoyi.study.domain.StudyReservation;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/1/15 17:24
 */
public interface StudyReservationService {
    /**
     * 获取所有预约记录
     * @return 所有预约记录列表
     */
    List<StudyReservation> getAllReservations();

    /**
     * 根据预约ID获取预约信息
     * @param reservationId 预约ID
     * @return 预约信息
     */
    StudyReservation getReservationById(Long reservationId);

    /**
     * 根据用户ID获取预约记录
     * @param userId 用户ID
     * @return 用户的所有预约记录
     */
    List<StudyReservation> getReservationsByUserId(Long userId);

    /**
     * 根据座位ID获取预约记录
     * @param seatId 座位ID
     * @return 座位的所有预约记录
     */
    List<StudyReservation> getReservationsBySeatId(Long seatId);

    /**
     * 添加预约记录
     * @param studyReservation 待添加的预约信息
     * @return 添加成功返回 true，否则返回 false
     */
    boolean addReservation(StudyReservation studyReservation);

    /**
     * 更新预约记录
     * @param studyReservation 待更新的预约信息
     * @return 更新成功返回 true，否则返回 false
     */
    boolean updateReservation(StudyReservation studyReservation);

    /**
     * 删除预约记录
     * @param reservationId 待删除的预约ID
     * @return 删除成功返回 true，否则返回 false
     */
    boolean deleteReservation(Long reservationId);
}
