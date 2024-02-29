package com.ruoyi.featherball.mapper;

/**
 *
 * 场地管理 Mapper 接口
 *
 * @Author fanjaixing
 * @Date 2024/2/29 22:54
 */

import com.ruoyi.featherball.domain.Court;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourtMapper {

    List<Court> getAllCourts();

    Court getCourtById(Long courtId);

    int addCourt(Court court);

    int updateCourt(Court court);

    int deleteCourt(Long courtId);
}
