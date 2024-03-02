package com.ruoyi.featherball.domain;

/**
 *
 * 羽毛球场预约表
 *
 * @Author fanjaixing
 * @Date 2024/2/29 21:09
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.enums.ReservationStatusEnum;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("cfcr_reservation")
@Getter
@Setter
@ToString
public class Reservation implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long reservationId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 场地ID
     */
    private Long courtId;

    /**
     * 预约时间
     */
    private LocalDateTime reservationTime;

    /**
     * 预约状态，0预约，1取消
     */
    private ReservationStatusEnum reservationStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
