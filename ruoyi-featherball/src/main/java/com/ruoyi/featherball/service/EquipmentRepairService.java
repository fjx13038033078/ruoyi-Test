package com.ruoyi.featherball.service;

import com.ruoyi.featherball.domain.EquipmentRepair;

import java.util.List;

/**
 *商品维修记录管理 Service 接口
 *
 * @Author 范佳兴
 * @date 2024/3/8 11:43
 */
public interface EquipmentRepairService {
    List<EquipmentRepair> getAllEquipmentRepairs();

    EquipmentRepair getEquipmentRepairById(Long equipmentRepairId);

    boolean addEquipmentRepair(EquipmentRepair equipmentRepair);

    boolean updateEquipmentRepair(EquipmentRepair equipmentRepair);

    boolean deleteEquipmentRepair(Long equipmentRepairId);
}
