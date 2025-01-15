package com.ruoyi.study.service.impl;

import com.ruoyi.study.domain.StudySeat;
import com.ruoyi.study.mapper.StudySeatMapper;
import com.ruoyi.study.service.StudySeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/1/15 17:22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StudySeatServiceImpl implements StudySeatService {

    private final StudySeatMapper studySeatMapper;

    /**
     * 获取所有座位信息
     * @return 所有座位列表
     */
    @Override
    public List<StudySeat> getAllSeats() {
        return studySeatMapper.getAllSeats();
    }

    /**
     * 根据座位ID获取座位信息
     * @param seatId 座位ID
     * @return 座位信息
     */
    @Override
    public StudySeat getSeatById(Long seatId) {
        return studySeatMapper.getSeatById(seatId);
    }

    /**
     * 根据自习室ID获取所有座位
     * @param roomId 自习室ID
     * @return 自习室内的所有座位
     */
    @Override
    public List<StudySeat> getSeatsByRoomId(Long roomId) {
        return studySeatMapper.getSeatsByRoomId(roomId);
    }

    /**
     * 添加座位信息
     * @param studySeat 待添加的座位信息
     * @return 添加成功返回 true，否则返回 false
     */
    @Override
    public boolean addSeat(StudySeat studySeat) {
        int rows = studySeatMapper.addSeat(studySeat);
        return rows > 0;
    }

    /**
     * 更新座位信息
     * @param studySeat 待更新的座位信息
     * @return 更新成功返回 true，否则返回 false
     */
    @Override
    public boolean updateSeat(StudySeat studySeat) {
        int rows = studySeatMapper.updateSeat(studySeat);
        return rows > 0;
    }

    /**
     * 删除座位信息
     * @param seatId 待删除的座位ID
     * @return 删除成功返回 true，否则返回 false
     */
    @Override
    public boolean deleteSeat(Long seatId) {
        int rows = studySeatMapper.deleteSeat(seatId);
        return rows > 0;
    }
}
