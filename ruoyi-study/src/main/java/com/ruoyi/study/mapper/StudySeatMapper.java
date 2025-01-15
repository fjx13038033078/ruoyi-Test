package com.ruoyi.study.mapper;

import com.ruoyi.study.domain.StudySeat;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/1/15 16:57
 */
@Mapper
public interface StudySeatMapper {
    /**
     * 获取所有座位
     *
     * @return 所有座位列表
     */
    List<StudySeat> getAllSeats();

    /**
     * 根据座位ID获取座位信息
     *
     * @param seatId 座位ID
     * @return 座位信息
     */
    StudySeat getSeatById(Long seatId);

    /**
     * 根据自习室ID获取所有座位
     *
     * @param roomId 自习室ID
     * @return 自习室内的所有座位
     */
    List<StudySeat> getSeatsByRoomId(Long roomId);

    /**
     * 添加座位
     *
     * @param studySeat 待添加的座位信息
     * @return 添加成功返回影响的行数，否则返回0
     */
    int addSeat(StudySeat studySeat);

    /**
     * 更新座位信息
     *
     * @param studySeat 待更新的座位信息
     * @return 更新成功返回影响的行数，否则返回0
     */
    int updateSeat(StudySeat studySeat);

    /**
     * 删除座位
     *
     * @param seatId 待删除的座位ID
     * @return 删除成功返回影响的行数，否则返回0
     */
    int deleteSeat(Long seatId);
}
