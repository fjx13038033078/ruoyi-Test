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

/**
 * @Author 范佳兴
 * @date 2025/1/15 16:42
 */
@TableName("study_seat")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class StudySeat implements Serializable {
    /**
     * 座位ID
     */
    @TableId(type = IdType.AUTO)
    private Long seatId;

    /**
     * 自习室ID
     */
    private Long roomId;

    /**
     * 自习室名称
     */
    @TableField(exist = false)
    private String roomName;

    /**
     * 座位编号
     */
    private String seatNumber;

    /**
     * 座位状态（1-可用，0-不可用）
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
