package com.kevin.cms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kevin.cms.entity.CrmBanner;
import com.kevin.cms.service.CrmBannerService;
import com.kevin.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-08-02 22:13
 */
@RestController
@Api(tags = "网站后台")
@RequestMapping("/educms/banneradmin")
public class BannerAdminController {
    @Autowired
    private CrmBannerService bannerService;

    /**
     * 分页查询banner
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation(value = "分页查询banner")
    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit) {
        Page<CrmBanner> pageBanner = new Page<>(page, limit);
        bannerService.page(pageBanner, null);
        return R.ok().data("items", pageBanner.getRecords()).data("total", pageBanner.getTotal());
    }

    /**
     * 添加banner
     * @param crmBanner
     * @return
     */
    @ApiOperation(value = "添加banner")
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.save(crmBanner);
        return R.ok();
    }

    /**
     * 根据id获取banner
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取banner")
    @GetMapping("/get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item", banner);
    }

    /**
     * 修改banner
     * @param crmBanner
     * @return
     */
    @ApiOperation(value = "修改banner")
    @PostMapping("/update")
    public R updateById(@RequestBody CrmBanner crmBanner) {
        bannerService.updateById(crmBanner);
        return R.ok();
    }
}
