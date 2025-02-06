package com.ruoyi.web.controller.university;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.university.domain.Province;
import com.ruoyi.university.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/2/5 14:27
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/university/province")
public class ProvinceController extends BaseController {
    private final ProvinceService provinceService;

    /**
     * 获取所有省份的高校统计信息列表
     * @return 省份高校统计信息列表
     */
    @GetMapping("/listAll")
    public TableDataInfo listAllProvinces() {
        startPage();
        List<Province> allProvinces = provinceService.getAllProvinces();
        return getDataTable(allProvinces);
    }

    /**
     * 根据省份ID获取高校统计信息
     * @param provinceId 省份ID
     * @return 省份信息
     */
    @GetMapping("/detail")
    public AjaxResult getProvinceById(@RequestParam Long provinceId) {
        return success(provinceService.getProvinceById(provinceId));
    }

    /**
     * 添加省份高校统计信息
     * @param province 待添加的省份信息
     * @return 操作结果
     */
    @PostMapping("/add")
    public AjaxResult addProvince(@RequestBody Province province) {
        return toAjax(provinceService.addProvince(province));
    }

    /**
     * 更新省份高校统计信息
     * @param province 待更新的省份信息
     * @return 操作结果
     */
    @PostMapping("/update")
    public AjaxResult updateProvince(@RequestBody Province province) {
        return toAjax(provinceService.updateProvince(province));
    }

    /**
     * 删除省份高校统计信息
     * @param provinceId 待删除的省份ID
     * @return 操作结果
     */
    @GetMapping("/delete")
    public AjaxResult deleteProvince(@RequestParam Long provinceId) {
        return toAjax(provinceService.deleteProvince(provinceId));
    }
}
