package com.ruoyi.study.mapper;

import com.ruoyi.study.domain.StudyRoom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/1/15 16:53
 */
@Mapper
public interface StudyRoomMapper {
    /**
     * 获取所有自习室
     *
     * @return 所有自习室列表
     */
    List<StudyRoom> getAllStudyRooms();

    /**
     * 根据自习室ID获取自习室信息
     *
     * @param roomId 自习室ID
     * @return 自习室信息
     */
    StudyRoom getStudyRoomById(Long roomId);

    /**
     * 添加自习室
     *
     * @param studyRoom 待添加的自习室信息
     * @return 添加成功返回影响的行数，否则返回0
     */
    int addStudyRoom(StudyRoom studyRoom);

    /**
     * 更新自习室信息
     *
     * @param studyRoom 待更新的自习室信息
     * @return 更新成功返回影响的行数，否则返回0
     */
    int updateStudyRoom(StudyRoom studyRoom);

    /**
     * 删除自习室
     *
     * @param roomId 待删除的自习室ID
     * @return 删除成功返回影响的行数，否则返回0
     */
    int deleteStudyRoom(Long roomId);
}
