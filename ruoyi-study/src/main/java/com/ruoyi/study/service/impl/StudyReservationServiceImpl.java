package com.ruoyi.study.service.impl;

import com.ruoyi.study.domain.StudyReservation;
import com.ruoyi.study.mapper.StudyReservationMapper;
import com.ruoyi.study.service.StudyReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/1/15 17:25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StudyReservationServiceImpl implements StudyReservationService {

    private final StudyReservationMapper studyReservationMapper;

    /**
     * 获取所有预约记录
     * @return 所有预约记录列表
     */
    @Override
    public List<StudyReservation> getAllReservations() {
        return studyReservationMapper.getAllReservations();
    }

    /**
     * 根据预约ID获取预约信息
     * @param reservationId 预约ID
     * @return 预约信息
     */
    @Override
    public StudyReservation getReservationById(Long reservationId) {
        return studyReservationMapper.getReservationById(reservationId);
    }

    /**
     * 根据用户ID获取预约记录
     * @param userId 用户ID
     * @return 用户的所有预约记录
     */
    @Override
    public List<StudyReservation> getReservationsByUserId(Long userId) {
        return studyReservationMapper.getReservationsByUserId(userId);
    }

    /**
     * 根据座位ID获取预约记录
     * @param seatId 座位ID
     * @return 座位的所有预约记录
     */
    @Override
    public List<StudyReservation> getReservationsBySeatId(Long seatId) {
        return studyReservationMapper.getReservationsBySeatId(seatId);
    }

    /**
     * 添加预约记录
     * @param studyReservation 待添加的预约信息
     * @return 添加成功返回 true，否则返回 false
     */
    @Override
    public boolean addReservation(StudyReservation studyReservation) {
        int rows = studyReservationMapper.addReservation(studyReservation);
        return rows > 0;
    }

    /**
     * 更新预约记录
     * @param studyReservation 待更新的预约信息
     * @return 更新成功返回 true，否则返回 false
     */
    @Override
    public boolean updateReservation(StudyReservation studyReservation) {
        int rows = studyReservationMapper.updateReservation(studyReservation);
        return rows > 0;
    }

    /**
     * 删除预约记录
     * @param reservationId 待删除的预约ID
     * @return 删除成功返回 true，否则返回 false
     */
    @Override
    public boolean deleteReservation(Long reservationId) {
        int rows = studyReservationMapper.deleteReservation(reservationId);
        return rows > 0;
    }
}
