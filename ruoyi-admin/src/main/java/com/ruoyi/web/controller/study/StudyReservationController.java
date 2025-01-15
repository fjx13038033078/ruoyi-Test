package com.ruoyi.web.controller.study;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.study.domain.StudyReservation;
import com.ruoyi.study.service.StudyReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/1/15 17:43
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/study/reservation")
public class StudyReservationController extends BaseController {

    private final StudyReservationService studyReservationService;

    /**
     * 获取所有预约记录
     * @return 预约记录列表
     */
    @GetMapping("/listAll")
    public TableDataInfo listAllReservations() {
        startPage();
        List<StudyReservation> allReservations = studyReservationService.getAllReservations();
        return getDataTable(allReservations);
    }

    /**
     * 根据预约ID获取预约信息
     * @param reservationId 预约ID
     * @return 预约信息
     */
    @GetMapping("/detail")
    public AjaxResult getReservationById(@RequestParam Long reservationId) {
        return success(studyReservationService.getReservationById(reservationId));
    }

    /**
     * 根据用户ID获取预约记录
     * @param userId 用户ID
     * @return 用户的所有预约记录
     */
    @GetMapping("/listByUserId")
    public TableDataInfo getReservationsByUserId(@RequestParam Long userId) {
        startPage();
        List<StudyReservation> reservationsByUser = studyReservationService.getReservationsByUserId(userId);
        return getDataTable(reservationsByUser);
    }

    /**
     * 根据座位ID获取预约记录
     * @param seatId 座位ID
     * @return 座位的所有预约记录
     */
    @GetMapping("/listBySeatId")
    public TableDataInfo getReservationsBySeatId(@RequestParam Long seatId) {
        startPage();
        List<StudyReservation> reservationsBySeat = studyReservationService.getReservationsBySeatId(seatId);
        return getDataTable(reservationsBySeat);
    }

    /**
     * 添加预约记录
     * @param studyReservation 待添加的预约信息
     * @return 操作结果
     */
    @PostMapping("/add")
    public AjaxResult addReservation(@RequestBody StudyReservation studyReservation) {
        return toAjax(studyReservationService.addReservation(studyReservation));
    }

    /**
     * 更新预约记录
     * @param studyReservation 待更新的预约信息
     * @return 操作结果
     */
    @PostMapping("/update")
    public AjaxResult updateReservation(@RequestBody StudyReservation studyReservation) {
        return toAjax(studyReservationService.updateReservation(studyReservation));
    }

    /**
     * 删除预约记录
     * @param reservationId 待删除的预约ID
     * @return 操作结果
     */
    @GetMapping("/delete")
    public AjaxResult deleteReservation(@RequestParam Long reservationId) {
        return toAjax(studyReservationService.deleteReservation(reservationId));
    }
}
