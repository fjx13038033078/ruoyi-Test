package com.ruoyi.web.controller.study;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.study.domain.StudySeat;
import com.ruoyi.study.service.StudySeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/1/15 17:42
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/study/seat")
public class StudySeatController extends BaseController {
    private final StudySeatService studySeatService;

    /**
     * 获取所有座位信息
     * @return 座位列表
     */
    @GetMapping("/listAll")
    public TableDataInfo listAllSeats() {
        startPage();
        List<StudySeat> allSeats = studySeatService.getAllSeats();
        return getDataTable(allSeats);
    }

    /**
     * 根据座位ID获取座位信息
     * @param seatId 座位ID
     * @return 座位信息
     */
    @GetMapping("/detail")
    public AjaxResult getSeatById(@RequestParam Long seatId) {
        return success(studySeatService.getSeatById(seatId));
    }

    /**
     * 根据自习室ID获取所有座位
     * @param roomId 自习室ID
     * @return 自习室内的所有座位
     */
    @GetMapping("/listByRoomId")
    public TableDataInfo getSeatsByRoomId(@RequestParam Long roomId) {
        startPage();
        List<StudySeat> seatsByRoom = studySeatService.getSeatsByRoomId(roomId);
        return getDataTable(seatsByRoom);
    }

    /**
     * 添加座位信息
     * @param studySeat 待添加的座位信息
     * @return 操作结果
     */
    @PostMapping("/add")
    public AjaxResult addSeat(@RequestBody StudySeat studySeat) {
        return toAjax(studySeatService.addSeat(studySeat));
    }

    /**
     * 更新座位信息
     * @param studySeat 待更新的座位信息
     * @return 操作结果
     */
    @PostMapping("/update")
    public AjaxResult updateSeat(@RequestBody StudySeat studySeat) {
        return toAjax(studySeatService.updateSeat(studySeat));
    }

    /**
     * 删除座位信息
     * @param seatId 待删除的座位ID
     * @return 操作结果
     */
    @GetMapping("/delete")
    public AjaxResult deleteSeat(@RequestParam Long seatId) {
        return toAjax(studySeatService.deleteSeat(seatId));
    }
}
