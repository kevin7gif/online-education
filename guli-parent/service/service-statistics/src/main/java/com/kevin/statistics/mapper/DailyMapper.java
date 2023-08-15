package com.kevin.statistics.mapper;

import com.kevin.statistics.entity.Daily;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 网站统计日数据 Mapper 接口
 * </p>
 *
 * @author kevin
 * @since 2023-08-14
 */
@Mapper
public interface DailyMapper extends BaseMapper<Daily> {

    // 删除某一天的统计记录
    void deleteByDay(String day);
}
