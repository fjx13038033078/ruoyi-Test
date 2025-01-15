package com.ruoyi.study.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author 范佳兴
 * @date 2025/1/15 16:43
 */
@TableName("study_reservation")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class StudyReservation implements Serializable {

    /**
     * 预约ID
     */
    @TableId(type = IdType.AUTO)
    private Long reservationId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 座位ID
     */
    private Long seatId;

    /**
     * 座位编号
     */
    @TableField(exist = false)
    private String seatNumber;

    /**
     * 自习室名称
     */
    @TableField(exist = false)
    private String roomName;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 预约状态（0-未使用，1-已使用，2-取消）
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
