package com.ruoyi.university.service.impl;

import com.ruoyi.university.domain.Province;
import com.ruoyi.university.mapper.ProvinceMapper;
import com.ruoyi.university.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/2/5 11:48
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceMapper provinceMapper;

    /**
     * 获取所有省份的高校统计信息
     *
     * @return 省份列表
     */
    @Override
    public List<Province> getAllProvinces() {
        return provinceMapper.getAllProvinces();
    }

    /**
     * 根据省份ID获取高校统计信息
     *
     * @param provinceId 省份ID
     * @return 省份信息
     */
    @Override
    public Province getProvinceById(Long provinceId) {
        return provinceMapper.getProvinceById(provinceId);
    }

    /**
     * 添加省份高校统计信息
     *
     * @param province 待添加的省份信息
     * @return 添加成功返回 true，否则返回 false
     */
    @Override
    public boolean addProvince(Province province) {
        int rows = provinceMapper.addProvince(province);
        return rows > 0;
    }

    /**
     * 更新省份高校统计信息
     *
     * @param province 待更新的省份信息
     * @return 更新成功返回 true，否则返回 false
     */
    @Override
    public boolean updateProvince(Province province) {
        int rows = provinceMapper.updateProvince(province);
        return rows > 0;
    }

    /**
     * 删除省份高校统计信息
     *
     * @param provinceId 待删除的省份ID
     * @return 删除成功返回 true，否则返回 false
     */
    @Override
    public boolean deleteProvince(Long provinceId) {
        int rows = provinceMapper.deleteProvince(provinceId);
        return rows > 0;
    }
}
