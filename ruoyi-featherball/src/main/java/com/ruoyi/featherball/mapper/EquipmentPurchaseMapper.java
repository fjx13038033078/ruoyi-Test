package com.ruoyi.featherball.mapper;

import com.ruoyi.featherball.domain.EquipmentPurchase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2024/3/8 11:19
 */
@Mapper
public interface EquipmentPurchaseMapper {
    List<EquipmentPurchase> getAllEquipmentPurchases();

    EquipmentPurchase getEquipmentPurchaseById(Long equipmentPurchaseId);

    int addEquipmentPurchase(EquipmentPurchase equipmentPurchase);

    int updateEquipmentPurchase(EquipmentPurchase equipmentPurchase);

    int deleteEquipmentPurchase(Long equipmentPurchaseId);

}
