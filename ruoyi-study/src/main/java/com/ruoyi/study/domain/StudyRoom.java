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
 * @date 2025/1/15 16:40
 */
@TableName("study_room")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class StudyRoom implements Serializable {
    /**
     * 自习室ID
     */
    @TableId(type = IdType.AUTO)
    private Long roomId;

    /**
     * 自习室名称
     */
    private String roomName;

    /**
     * 自习室总容纳人数
     */
    private Integer capacity;

    /**
     * 自习室位置
     */
    private String location;

    /**
     * 自习室状态（1-开放，0-关闭）
     */
    private Integer status;

    /**
     * 自习室描述
     */
    private String description;

    /**
     * 自习室照片
     */
    private String image;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
