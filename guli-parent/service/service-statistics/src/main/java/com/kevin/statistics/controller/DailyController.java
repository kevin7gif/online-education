package com.kevin.statistics.controller;


import com.kevin.statistics.service.DailyService;
import com.kevin.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author kevin
 * @since 2023-08-14
 */
@RestController
@RequestMapping("/statistics")
@Api(tags = "统计分析管理")
public class DailyController {
    @Autowired
    private DailyService dailyService;

    /**
     * 统计某一天的注册人数
     * @param day
     * @return
     */
    @PostMapping("/registerCount/{day}")
    @ApiOperation("统计某一天的注册人数")
    public R registerCount(@PathVariable String day) {
        dailyService.registerCount(day);
        return R.ok();
    }

    @GetMapping("/showData/{type}/{begin}/{end}")
    @ApiOperation("显示统计数据")
    public R showChart( @PathVariable String type,@PathVariable String begin, @PathVariable String end) {
        Map<String,Object> map=dailyService.showChart(type,begin, end);
        return R.ok().data("data", map);
    }

}

