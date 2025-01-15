package com.ruoyi.study.service.impl;

import com.ruoyi.study.domain.StudyRoom;
import com.ruoyi.study.mapper.StudyRoomMapper;
import com.ruoyi.study.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/1/15 17:17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StudyRoomServiceImpl implements StudyRoomService {

    private final StudyRoomMapper studyRoomMapper;

    /**
     * 获取所有自习室信息
     * @return 所有自习室列表
     */
    @Override
    public List<StudyRoom> getAllStudyRooms() {
        return studyRoomMapper.getAllStudyRooms();
    }

    /**
     * 根据自习室ID获取自习室信息
     * @param roomId 自习室ID
     * @return 自习室信息
     */
    @Override
    public StudyRoom getStudyRoomById(Long roomId) {
        return studyRoomMapper.getStudyRoomById(roomId);
    }

    /**
     * 添加自习室信息
     * @param studyRoom 待添加的自习室信息
     * @return 添加成功返回 true，否则返回 false
     */
    @Override
    public boolean addStudyRoom(StudyRoom studyRoom) {
        int rows = studyRoomMapper.addStudyRoom(studyRoom);
        return rows > 0;
    }

    /**
     * 更新自习室信息
     * @param studyRoom 待更新的自习室信息
     * @return 更新成功返回 true，否则返回 false
     */
    @Override
    public boolean updateStudyRoom(StudyRoom studyRoom) {
        int rows = studyRoomMapper.updateStudyRoom(studyRoom);
        return rows > 0;
    }

    /**
     * 删除自习室信息
     * @param roomId 待删除的自习室ID
     * @return 删除成功返回 true，否则返回 false
     */
    @Override
    public boolean deleteStudyRoom(Long roomId) {
        int rows = studyRoomMapper.deleteStudyRoom(roomId);
        return rows > 0;
    }
}
