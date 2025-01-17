package com.ruoyi.web.controller.study;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.study.domain.Comment;
import com.ruoyi.study.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/1/17 15:10
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/study/comment")
public class CommentController extends BaseController {

    private final CommentService commentService;

    /**
     * 根据自习室ID获取评论列表
     *
     * @param roomId 自习室ID
     * @return 评论列表
     */
    @GetMapping("/listByRoomId")
    public TableDataInfo listCommentsByRoomId(@RequestParam Long roomId) {
        startPage();
        List<Comment> comments = commentService.listCommentsByRoomId(roomId);
        return getDataTable(comments);
    }

    /**
     * 根据评论ID获取评论详情
     *
     * @param commentId 评论ID
     * @return 评论详情
     */
    @GetMapping("/get")
    public AjaxResult getCommentById(@RequestParam Long commentId) {
        return success(commentService.getCommentById(commentId));
    }

    /**
     * 添加评论
     *
     * @param comment 评论对象
     * @return 操作结果
     */
    @PostMapping("/add")
    public AjaxResult addComment(@RequestBody Comment comment) {
        return toAjax(commentService.addComment(comment));
    }

    /**
     * 删除评论
     *
     * @param commentId 评论ID
     * @return 操作结果
     */
    @GetMapping("/delete")
    public AjaxResult deleteComment(@RequestParam Long commentId) {
        return toAjax(commentService.deleteComment(commentId));
    }
}
