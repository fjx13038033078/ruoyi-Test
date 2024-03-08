package com.ruoyi.featherball.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author 范佳兴
 * @date 2024/3/8 11:09
 */
@TableName("cfcr_equipment_purchase")
@Getter
@Setter
@ToString
public class EquipmentPurchase implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long equipmentPurchaseId;

    /**
     * 场馆ID
     */
    private Long venueId;

    /**
     * 商品类别
     */
    private Integer equipmentPurchaseType;

    /**
     * 商品个数
     */
    private Integer equipmentPurchaseNumber;

    /**
     * 商品单价
     */
    private BigDecimal equipmentPurchaseFee;

    private static final long serialVersionUID = 1L;
}
