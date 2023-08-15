package com.kevin.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kevin.statistics.client.UCenterClient;
import com.kevin.statistics.entity.Daily;
import com.kevin.statistics.mapper.DailyMapper;
import com.kevin.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author kevin
 * @since 2023-08-14
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Resource
    private UCenterClient uCenterClient;

    // 统计某一天的注册人数
    @Override
    public void registerCount(String day) {
        // 添加记录之前先删除表相同日期的数据
        baseMapper.deleteByDay(day);
        // 远程调用得到某一天的注册人数
        Integer countRegister = (Integer) uCenterClient.countRegister(day).getData().get("countRegister");

        // 把获取数据添加数据库，统计分析表里面
        Daily daily = new Daily();
        daily.setRegisterNum(countRegister); // 注册人数
        daily.setDateCalculated(day); // 统计日期
        daily.setVideoViewNum(RandomUtils.nextInt(10,50)); // 视频播放数
        daily.setLoginNum(RandomUtils.nextInt(10,50)); // 登录人数
        daily.setCourseNum(RandomUtils.nextInt(10,50)); // 课程数
        baseMapper.insert(daily);

    }

    // 图表显示，返回两部分数据，日期json数组，数量json数组
    @Override
    public Map<String, Object> showChart(String type, String begin, String end) {
        // 根据条件查询数据
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select(type,"date_calculated");
        wrapper.orderByAsc("date_calculated");
        List<Daily> dailyList = baseMapper.selectList(wrapper);

        // 因为返回有两部分数据，日期和日期对应数量，所以创建两个集合
        // 用于存储日期集合
        List<String> date_calculatedList = new ArrayList<>();
        // 用于存储日期对应数量
        List<Integer> numDataList = new ArrayList<>();

        // 遍历查询所有数据list集合进行封装
        for (int i = 0; i < dailyList.size(); i++) {
            Daily daily = dailyList.get(i);
            // 封装日期list集合
            date_calculatedList.add(daily.getDateCalculated());
            // 封装日期对应数量
            switch (type) {
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        // 把封装之后两部分数据放到map集合里面返回
        Map<String, Object> map = new java.util.HashMap<>();
        map.put("date_calculatedList", date_calculatedList);
        map.put("numDataList", numDataList);

        return map;
    }
}
