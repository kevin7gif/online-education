package com.kevin.statistics.service;

import com.kevin.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author kevin
 * @since 2023-08-14
 */
public interface DailyService extends IService<Daily> {

    // 统计某一天的注册人数
    void registerCount(String day);

    // 图表显示，返回两部分数据，日期json数组，数量json数组
    Map<String, Object> showChart(String type, String begin, String end);
}
