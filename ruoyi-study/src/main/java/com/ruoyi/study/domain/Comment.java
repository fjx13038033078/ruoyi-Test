package com.ruoyi.study.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author 范佳兴
 * @date 2025/1/17 15:01
 */
@TableName("study_comment")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Comment implements Serializable {
    /**
     * 评论ID，主键，自增
     */
    @TableId(type = IdType.AUTO)
    private Long commentId;

    /**
     * 自习室ID，外键
     */
    private Long roomId;

    /**
     * 评论用户ID，外键
     */
    private Long userId;

    /**
     * 用户姓名
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
