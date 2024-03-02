package com.ruoyi.featherball.service.impl;

import com.ruoyi.featherball.domain.Court;
import com.ruoyi.featherball.mapper.CourtMapper;
import com.ruoyi.featherball.mapper.VenueMapper;
import com.ruoyi.featherball.service.CourtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * 场地管理 Service 实现类
 *
 * @Author fanjaixing
 * @Date 2024/2/29 22:55
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CourtServiceImpl implements CourtService {

    private final CourtMapper courtMapper;

    private final VenueMapper venueMapper;

    /**
     * 获取所有场地
     * @return 所有场地列表
     */
    @Override
    public List<Court> getAllCourts() {
        List<Court> allCourts = courtMapper.getAllCourts();
        for (Court court : allCourts){
            // 根据 court 中的 venueId 查询对应的场馆名称
            String venueName = venueMapper.getVenueById(court.getVenueId()).getVenueName();
            // 将场馆名称设置到 Court 对象中
            court.setVenueName(venueName);
        }
        return allCourts;
    }

    /**
     * 根据场地ID获取场地信息
     * @param courtId 场地ID
     * @return 场地信息
     */
    @Override
    public Court getCourtById(Long courtId) {
        return courtMapper.getCourtById(courtId);
    }

    /**
     * 添加场地
     * @param court 待添加的场地信息
     * @return 添加成功返回 true，否则返回 false
     */
    @Override
    public boolean addCourt(Court court) {
        countByCourtNumber(court);
        int rows = courtMapper.addCourt(court);
        return rows > 0;
    }

    /**
     * 更新场地信息
     * @param court 待更新的场地信息
     * @return 更新成功返回 true，否则返回 false
     */
    @Override
    public boolean updateCourt(Court court) {
        countByCourtNumber(court);
        int rows = courtMapper.updateCourt(court);
        return rows > 0;
    }

    /**
     * 删除场地
     * @param courtId 待删除的场地ID
     * @return 删除成功返回 true，否则返回 false
     */
    @Override
    public boolean deleteCourt(Long courtId) {
        int rows = courtMapper.deleteCourt(courtId);
        return rows > 0;
    }

    /**
     * 判断该场馆中该场地是否已存在
     *
     * @param court
     */
    private void countByCourtNumber(Court court){
        if (courtMapper.countByCourtNumber(court.getVenueId(), court.getCourtNumber()) > 0) {
            // 如果场地编号已存在，则抛出异常
            throw new RuntimeException("该场地编号已存在");
        }
    }
}
