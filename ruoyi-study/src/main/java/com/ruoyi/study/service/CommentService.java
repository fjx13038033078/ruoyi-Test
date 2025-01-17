package com.ruoyi.study.service;

import com.ruoyi.study.domain.Comment;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/1/17 15:06
 */
public interface CommentService {
    /**
     * 根据评论ID获取评论信息
     *
     * @param commentId 评论ID
     * @return 评论信息
     */
    Comment getCommentById(Long commentId);

    /**
     * 添加评论
     *
     * @param comment 待添加的评论信息
     * @return 添加成功返回 true，否则返回 false
     */
    boolean addComment(Comment comment);

    /**
     * 删除评论
     *
     * @param commentId 待删除的评论ID
     * @return 删除成功返回 true，否则返回 false
     */
    boolean deleteComment(Long commentId);

    /**
     * 根据自习室ID获取评论列表
     *
     * @param roomId 自习室ID
     * @return 评论列表
     */
    List<Comment> listCommentsByRoomId(Long roomId);
}
