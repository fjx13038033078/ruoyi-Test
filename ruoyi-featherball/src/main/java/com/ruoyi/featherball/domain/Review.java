package com.ruoyi.featherball.domain;

/**
 *
 * 场地评价表
 *
 * @Author fanjaixing
 * @Date 2024/2/29 21:10
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("cfcr_review")
@Getter
@Setter
@ToString
public class Review implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long reviewId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 场地ID
     */
    private Long courtId;

    /**
     * 评论内容
     */
    private String comment;

    /**
     * 评论时间
     */
    private LocalDateTime reviewTime;

    private static final long serialVersionUID = 1L;
}

