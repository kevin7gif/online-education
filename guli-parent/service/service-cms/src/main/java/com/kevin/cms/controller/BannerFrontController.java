package com.kevin.cms.controller;

import com.kevin.cms.entity.CrmBanner;
import com.kevin.cms.service.CrmBannerService;
import com.kevin.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-08-02 22:13
 */
@RestController
@Api(tags = "网站前台")
@RequestMapping("/educms/bannerfront")
public class BannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

    /**
     * 查询所有banner
     * @return
     */
    @GetMapping("/getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list", list);
    }
}
