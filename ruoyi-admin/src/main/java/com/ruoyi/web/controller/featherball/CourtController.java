package com.ruoyi.web.controller.featherball;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.featherball.domain.Court;
import com.ruoyi.featherball.service.CourtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 场地Controller
 *
 * @Author fanjaixing
 * @Date 2024/3/1 22:24
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/featherBall/court")
public class CourtController extends BaseController {

    private final CourtService courtService;

    // 获取所有场地列表
    @GetMapping("/listAll")
    public TableDataInfo listAllCourts() {
        startPage();
        List<Court> allCourts = courtService.getAllCourts();
        return getDataTable(allCourts);
    }

    // 根据场地ID获取场地信息
    @GetMapping("/detail")
    public Court getCourtById(@RequestParam Long courtId) {
        return courtService.getCourtById(courtId);
    }

    // 添加场地
    @PostMapping("/add")
    public boolean addCourt(@RequestBody Court court) {
        return courtService.addCourt(court);
    }

    // 更新场地信息
    @PostMapping("/update")
    public boolean updateCourt(@RequestBody Court court) {
        return courtService.updateCourt(court);
    }

    // 删除场地
    @GetMapping("/delete")
    public boolean deleteCourt(@RequestParam Long courtId) {
        return courtService.deleteCourt(courtId);
    }
}
