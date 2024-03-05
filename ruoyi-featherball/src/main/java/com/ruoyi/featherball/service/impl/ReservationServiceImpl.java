package com.ruoyi.featherball.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.featherball.domain.Court;
import com.ruoyi.featherball.domain.Reservation;
import com.ruoyi.featherball.mapper.ReservationMapper;
import com.ruoyi.featherball.service.CourtService;
import com.ruoyi.featherball.service.ReservationService;
import com.ruoyi.system.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    private final CourtService courtService;

    private ISysRoleService roleService;

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
     *
     * @param reservationId 预约ID
     * @return 预约信息
     */
    @Override
    public Reservation getReservationById(Long reservationId) {
        return reservationMapper.getReservationById(reservationId);
    }

    /**
     * 添加预约信息
     *
     * @param reservation 待添加的预约信息
     * @return 添加成功返回 true，否则返回 false
     */
    @Override
    public boolean addReservation(Reservation reservation) {
        checkReservationTimeConflict(reservation);
        Long userId = SecurityUtils.getUserId(); // 获取当前登录用户的ID
        reservation.setUserId(userId);
        int rows = reservationMapper.addReservation(reservation);
        return rows > 0;
    }

    /**
     * 更新预约信息
     *
     * @param reservation 待更新的预约信息
     * @return 更新成功返回 true，否则返回 false
     */
    @Override
    public boolean updateReservation(Reservation reservation) {
        checkReservationTimeConflict(reservation);
        Long userId = SecurityUtils.getUserId(); // 获取当前登录用户的ID
        reservation.setUserId(userId);
        int rows = reservationMapper.updateReservation(reservation);
        return rows > 0;
    }

    /**
     * 删除预约信息
     *
     * @param reservationId 待删除的预约ID
     * @return 删除成功返回 true，否则返回 false
     */
    @Override
    public boolean deleteReservation(Long reservationId) {
        int rows = reservationMapper.deleteReservation(reservationId);
        return rows > 0;
    }

    /**
     * 如果该场地不可预约，或者该场地为 VIP 场地，且用户不是 VIP 用户，给出提示
     *
     * @param courtId 场地ID
     * @return 如果需要提示，则返回提示信息；否则返回空字符串
     */
    public void getVIPUserNotification(Long courtId) {
        Court court = courtService.getCourtById(courtId);
        // 从场地服务中获取场地状态
        Integer courtStatus = court.getCourtStatus();
        // 如果场地不可预约，则抛出异常
        if (courtStatus == 1) {
            throw new RuntimeException("该场地不可预约");
        }

        // 从场地服务中获取场地是否为 VIP 场地
        Integer courtVip = court.getCourtVip();
        // 获取当前登录用户的ID
        Long userId = SecurityUtils.getUserId();
        // 从角色服务中获取当前用户的角色
        String role = roleService.selectStringRoleByUserId(userId);

        // 如果场地为 VIP 场地且当前用户不是 VIP 用户，则抛出异常
        if (courtVip == 1 && !role.equalsIgnoreCase("VIP")) {
            throw new RuntimeException("您选择的场地为 VIP 场地，只有 VIP 用户才能预约，请先升级为 VIP 用户。");
        }
    }

    /**
     * 检查当前预约时间与场地其他预约时间是否存在冲突
     *
     * @param reservation 当前预约信息
     * @throws RuntimeException 如果当前预约时间与其他预约时间存在重叠部分，则抛出异常
     */
    private void checkReservationTimeConflict(Reservation reservation) {
        LocalDateTime startTime = reservation.getStartTime();
        LocalDateTime endTime = reservation.getEndTime();
        // 获取场地的所有预约信息
        List<Reservation> reservationList = reservationMapper.getReservationByCourtId(reservation.getCourtId());
        // 遍历场地的所有预约信息，检查是否存在时间冲突
        for (Reservation existingReservation : reservationList) {
            LocalDateTime existingStartTime = existingReservation.getStartTime();
            LocalDateTime existingEndTime = existingReservation.getEndTime();
            // 检查当前预约时间是否与其他预约时间发生重叠
            if ((startTime.isAfter(existingStartTime) && startTime.isBefore(existingEndTime)) ||
                    (endTime.isAfter(existingStartTime) && endTime.isBefore(existingEndTime)) ||
                    (startTime.isBefore(existingStartTime) && endTime.isAfter(existingEndTime))) {
                // 发生时间冲突，可以抛出异常或进行其他处理
                throw new RuntimeException("预约时间与其他预约存在冲突，请选择其他时间段！");
            }
        }
    }
}
