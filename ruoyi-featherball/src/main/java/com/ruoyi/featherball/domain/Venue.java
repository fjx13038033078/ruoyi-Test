package com.ruoyi.featherball.domain;

/**
 *
 * 场馆表
 *
 * @Author fanjaixing
 * @Date 2024/2/29 21:02
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
@TableName("cfcr_venue")
@Getter
@Setter
@ToString
public class Venue implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long venueId;

    /**
     * 场馆名称
     */
    private String venueName;

    /**
     * 场馆地址
     */
    private String venueAddress;

    /**
     * 场馆联系方式
     */
    private String venueContact;

    /**
     * 场馆描述
     */
    private String venueDescription;

    private static final long serialVersionUID = 1L;
}

