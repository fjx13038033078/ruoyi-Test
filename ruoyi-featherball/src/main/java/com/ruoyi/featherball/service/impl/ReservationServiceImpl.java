package com.ruoyi.featherball.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.featherball.domain.Reservation;
import com.ruoyi.featherball.mapper.ReservationMapper;
import com.ruoyi.featherball.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 场地预约service接口实现类
 *
 * @Author fanjaixing
 * @Date 2024/3/3 20:55
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationMapper reservationMapper;

    /**
     * 获取所有预约信息
     *
     * @return 所有预约信息列表
     */
    @Override
    public List<Reservation> getAllReservations() {
        return reservationMapper.getAllReservations();
    }

    /**
     * 根据预约ID获取预约信息
     * @param reservationId 预约ID
     * @return 预约信息
     */
    @Override
    public Reservation getReservationById(Long reservationId) {
        return reservationMapper.getReservationById(reservationId);
    }

    /**
     * 添加预约信息
     * @param reservation 待添加的预约信息
     * @return 添加成功返回 true，否则返回 false
     */
    @Override
    public boolean addReservation(Reservation reservation) {
        Long userId = SecurityUtils.getUserId(); // 获取当前登录用户的ID
        reservation.setUserId(userId);
        int rows = reservationMapper.addReservation(reservation);
        return rows > 0;
    }

    /**
     * 更新预约信息
     * @param reservation 待更新的预约信息
     * @return 更新成功返回 true，否则返回 false
     */
    @Override
    public boolean updateReservation(Reservation reservation) {
        int rows = reservationMapper.updateReservation(reservation);
        return rows > 0;
    }

    /**
     * 删除预约信息
     * @param reservationId 待删除的预约ID
     * @return 删除成功返回 true，否则返回 false
     */
    @Override
    public boolean deleteReservation(Long reservationId) {
        int rows = reservationMapper.deleteReservation(reservationId);
        return rows > 0;
    }
}
