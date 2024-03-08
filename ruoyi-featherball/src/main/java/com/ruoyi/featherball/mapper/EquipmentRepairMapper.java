package com.ruoyi.featherball.mapper;

import com.ruoyi.featherball.domain.EquipmentRepair;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2024/3/8 11:28
 */
@Mapper
public interface EquipmentRepairMapper {
    List<EquipmentRepair> getAllEquipmentRepairs();

    EquipmentRepair getEquipmentRepairById(Long equipmentRepairId);

    int addEquipmentRepair(EquipmentRepair equipmentRepair);

    int updateEquipmentRepair(EquipmentRepair equipmentRepair);

    int deleteEquipmentRepair(Long equipmentRepairId);

}
