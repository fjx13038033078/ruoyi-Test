package com.ruoyi.study.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.study.domain.Comment;
import com.ruoyi.study.mapper.CommentMapper;
import com.ruoyi.study.service.CommentService;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/1/17 15:07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    private final ISysUserService sysUserService;

    /**
     * 根据自习室ID获取评论列表
     *
     * @param roomId 自习室ID
     * @return 评论列表
     */
    @Override
    public List<Comment> listCommentsByRoomId(Long roomId) {
        List<Comment> comments = commentMapper.listCommentsByRoomId(roomId);
        for (Comment comment : comments) {
            Long userId = comment.getUserId();
            String nickName = sysUserService.selectUserById(userId).getNickName();
            comment.setUserName(nickName);
        }
        return comments;
    }

    /**
     * 根据评论ID获取评论信息
     *
     * @param commentId 评论ID
     * @return 评论信息
     */
    @Override
    public Comment getCommentById(Long commentId) {
        return commentMapper.getCommentById(commentId);
    }

    /**
     * 添加评论
     *
     * @param comment 评论信息
     * @return 是否添加成功
     */
    @Override
    public boolean addComment(Comment comment) {
        Long userId = SecurityUtils.getUserId();
        comment.setUserId(userId);
        comment.setCreateTime(LocalDateTime.now());
        int rows = commentMapper.addComment(comment);
        return rows > 0;
    }

    /**
     * 删除评论
     *
     * @param commentId 评论ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteComment(Long commentId) {
        Long userId = SecurityUtils.getUserId();
        Comment comment = commentMapper.getCommentById(commentId);
        if (comment != null && (userId == comment.getUserId() || userId == 1)) {
            int rows = commentMapper.deleteComment(commentId);
            return rows > 0;
        } else {
            throw new RuntimeException("仅可以删除自己的评论");
        }
    }
}
