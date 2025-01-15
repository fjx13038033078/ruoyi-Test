package com.ruoyi.web.controller.study;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.study.domain.StudyRoom;
import com.ruoyi.study.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/1/15 17:40
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/study/room")
public class StudyRoomController extends BaseController {

    private final StudyRoomService studyRoomService;

    /**
     * 获取所有自习室列表
     * @return 自习室列表
     */
    @GetMapping("/listAll")
    public TableDataInfo listAllStudyRooms() {
        startPage();
        List<StudyRoom> allStudyRooms = studyRoomService.getAllStudyRooms();
        return getDataTable(allStudyRooms);
    }

    /**
     * 根据自习室ID获取自习室信息
     * @param roomId 自习室ID
     * @return 自习室信息
     */
    @GetMapping("/detail")
    public AjaxResult getStudyRoomById(@RequestParam Long roomId) {
        return success(studyRoomService.getStudyRoomById(roomId));
    }

    /**
     * 添加自习室
     * @param studyRoom 待添加的自习室信息
     * @return 操作结果
     */
    @PostMapping("/add")
    public AjaxResult addStudyRoom(@RequestBody StudyRoom studyRoom) {
        return toAjax(studyRoomService.addStudyRoom(studyRoom));
    }

    /**
     * 更新自习室信息
     * @param studyRoom 待更新的自习室信息
     * @return 操作结果
     */
    @PostMapping("/update")
    public AjaxResult updateStudyRoom(@RequestBody StudyRoom studyRoom) {
        return toAjax(studyRoomService.updateStudyRoom(studyRoom));
    }

    /**
     * 删除自习室
     * @param roomId 待删除的自习室ID
     * @return 操作结果
     */
    @GetMapping("/delete")
    public AjaxResult deleteStudyRoom(@RequestParam Long roomId) {
        return toAjax(studyRoomService.deleteStudyRoom(roomId));
    }
}
