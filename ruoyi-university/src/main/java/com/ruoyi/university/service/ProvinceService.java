package com.ruoyi.university.service;

import com.ruoyi.university.domain.Province;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/2/5 11:47
 */
public interface ProvinceService {
    /**
     * 获取所有省份的高校统计信息
     *
     * @return 省份列表
     */
    List<Province> getAllProvinces();

    /**
     * 根据省份ID获取高校统计信息
     *
     * @param provinceId 省份ID
     * @return 省份信息
     */
    Province getProvinceById(Long provinceId);

    /**
     * 添加省份高校统计信息
     *
     * @param province 待添加的省份信息
     * @return 添加成功返回 true，否则返回 false
     */
    boolean addProvince(Province province);

    /**
     * 更新省份高校统计信息
     *
     * @param province 待更新的省份信息
     * @return 更新成功返回 true，否则返回 false
     */
    boolean updateProvince(Province province);

    /**
     * 删除省份高校统计信息
     *
     * @param provinceId 待删除的省份ID
     * @return 删除成功返回 true，否则返回 false
     */
    boolean deleteProvince(Long provinceId);
}
