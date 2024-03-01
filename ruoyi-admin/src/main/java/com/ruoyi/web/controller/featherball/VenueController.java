package com.ruoyi.web.controller.featherball;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.featherball.domain.Venue;
import com.ruoyi.featherball.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author fanjaixing
 * @Date 2024/3/1 22:24
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/featherBall/venue")
public class VenueController extends BaseController {

    private final VenueService venueService;

    // 获取所有场馆列表
    @GetMapping("/listAll")
    public TableDataInfo listAllVenues() {
        startPage();
        List<Venue> allVenues = venueService.getAllVenues();
        return getDataTable(allVenues);
    }

    // 根据场馆ID获取场馆信息
    @GetMapping("/detail")
    public Venue getVenueById(@RequestParam Long venueId) {
        return venueService.getVenueById(venueId);
    }

    // 添加场馆
    @PostMapping("/add")
    public boolean addVenue(@RequestBody Venue venue) {
        return venueService.addVenue(venue);
    }

    // 更新场馆信息
    @PostMapping("/update")
    public boolean updateVenue(@RequestBody Venue venue) {
        return venueService.updateVenue(venue);
    }

    // 删除场馆
    @GetMapping("/delete")
    public boolean deleteVenue(@RequestParam Long venueId) {
        return venueService.deleteVenue(venueId);
    }
}
