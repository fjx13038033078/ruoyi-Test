package com.ruoyi.study.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.study.domain.StudyReservation;
import com.ruoyi.study.domain.StudyRoom;
import com.ruoyi.study.mapper.StudyReservationMapper;
import com.ruoyi.study.service.StudyReservationService;
import com.ruoyi.study.service.StudyRoomService;
import com.ruoyi.study.service.StudySeatService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * @Author 范佳兴
 * @date 2025/1/15 17:25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StudyReservationServiceImpl implements StudyReservationService {

    private final StudyReservationMapper studyReservationMapper;

    private final ISysRoleService iSysRoleService;

    private final ISysUserService iSysUserService;

    private final StudyRoomService studyRoomService;

    private final StudySeatService studySeatService;

    /**
     * 获取所有预约记录
     *
     * @return 所有预约记录列表
     */
    @Override
    public List<StudyReservation> getAllReservations() {
        // 获取当前登录用户ID
        Long userId = SecurityUtils.getUserId();
        String role = iSysRoleService.selectStringRoleByUserId(userId);
        if (role.equalsIgnoreCase("admin")) {
            startPage();
            List<StudyReservation> allReservations = studyReservationMapper.getAllReservations();
            fillUserName(allReservations);
            fillRoomName(allReservations);
            return allReservations;
        } else {
            startPage();
            List<StudyReservation> reservationsByUserId = studyReservationMapper.getReservationsByUserId(userId);
            fillUserName(reservationsByUserId);
            return reservationsByUserId;
        }

    }

    /**
     * 根据预约ID获取预约信息
     *
     * @param reservationId 预约ID
     * @return 预约信息
     */
    @Override
    public StudyReservation getReservationById(Long reservationId) {
        return studyReservationMapper.getReservationById(reservationId);
    }

    /**
     * 根据用户ID获取预约记录
     *
     * @param userId 用户ID
     * @return 用户的所有预约记录
     */
    @Override
    public List<StudyReservation> getReservationsByUserId(Long userId) {
        return studyReservationMapper.getReservationsByUserId(userId);
    }

    /**
     * 根据座位ID获取预约记录
     *
     * @param seatId 座位ID
     * @return 座位的所有预约记录
     */
    @Override
    public List<StudyReservation> getReservationsBySeatId(Long seatId) {
        return studyReservationMapper.getReservationsBySeatId(seatId);
    }

    /**
     * 添加预约记录
     *
     * @param studyReservation 待添加的预约信息
     * @return 添加成功返回 true，否则返回 false
     */
    @Override
    public boolean addReservation(StudyReservation studyReservation) {
        Long userId = SecurityUtils.getUserId();
        studyReservation.setUserId(userId);
        checkReservationTimeConflict(studyReservation);
        int rows = studyReservationMapper.addReservation(studyReservation);
        return rows > 0;
    }

    /**
     * 更新预约记录
     *
     * @param studyReservation 待更新的预约信息
     * @return 更新成功返回 true，否则返回 false
     */
    @Override
    public boolean updateReservation(StudyReservation studyReservation) {
        checkReservationTimeConflict(studyReservation);
        int rows = studyReservationMapper.updateReservation(studyReservation);
        return rows > 0;
    }

    /**
     * 删除预约记录
     *
     * @param reservationId 待删除的预约ID
     * @return 删除成功返回 true，否则返回 false
     */
    @Override
    public boolean deleteReservation(Long reservationId) {
        int rows = studyReservationMapper.deleteReservation(reservationId);
        return rows > 0;
    }

    /**
     * 检查当前预约时间与座位其他预约时间是否存在冲突
     *
     * @param reservation 当前预约信息
     * @throws RuntimeException 如果当前预约时间与其他预约时间存在重叠部分，则抛出异常
     */
    private void checkReservationTimeConflict(StudyReservation reservation) {
        LocalDateTime startTime = reservation.getStartTime();
        LocalDateTime endTime = reservation.getEndTime();

        // 判断开始时间是否早于早八点
        LocalTime startTimeLimit = LocalTime.of(8, 0);
        if (startTime.toLocalTime().isBefore(startTimeLimit)) {
            throw new RuntimeException("开始时间不能早于早上8点！");
        }

        // 判断结束时间是否晚于晚10点
        LocalTime endTimeLimit = LocalTime.of(22, 0);
        if (endTime.toLocalTime().isAfter(endTimeLimit)) {
            throw new RuntimeException("结束时间不能晚于晚上10点！");
        }

        // 检查开始时间和结束时间之间的间隔是否超过三小时
        long durationHours = Duration.between(startTime, endTime).toHours();
        if (durationHours > 12) {
            throw new RuntimeException("预约时间段不能超过十二小时！");
        }

        // 获取座位的所有预约信息
        List<StudyReservation> reservationList = studyReservationMapper.getReservationsBySeatId(reservation.getSeatId())
                .stream()
                .filter(r -> r.getStatus() == 0)// 过滤掉状态为取消的预约
                .collect(Collectors.toList());// 将过滤后的预约信息收集到列表中

        // 遍历座位的所有预约信息，检查是否存在时间冲突
        for (StudyReservation existingReservation : reservationList) {
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

    /**
     * 为预约列表填充用户名
     *
     * @param reservations 预约列表
     */
    private void fillUserName(List<StudyReservation> reservations) {
        for (StudyReservation reservation : reservations) {
            Long userId = reservation.getUserId();
            SysUser user = iSysUserService.selectUserById(userId);
            if (user != null) {
                reservation.setUserName(user.getNickName());
            }
        }
    }

    /**
     * 为预约列表填充座位名
     *
     * @param reservations 预约列表
     */
    private void fillRoomName(List<StudyReservation> reservations) {
        for (StudyReservation reservation : reservations) {
            Long seatId = reservation.getSeatId();
            Long roomId = studySeatService.getSeatById(seatId).getRoomId();
            StudyRoom room = studyRoomService.getStudyRoomById(roomId);
            if (room != null) {
                reservation.setRoomName(room.getRoomName());
            }
        }
    }
}
