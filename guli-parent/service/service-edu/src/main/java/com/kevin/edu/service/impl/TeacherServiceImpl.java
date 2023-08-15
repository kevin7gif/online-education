package com.kevin.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kevin.edu.entity.Teacher;
import com.kevin.edu.mapper.TeacherMapper;
import com.kevin.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author kevin
 * @since 2023-07-20
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    // 分页查询讲师
    @Override
    public Map<String, Object> getTeacherFrontList(Page<Teacher> pageTeacher) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(pageTeacher, wrapper);

        List<Teacher> records = pageTeacher.getRecords();// 数据list集合
        long current = pageTeacher.getCurrent();// 当前页
        long pages = pageTeacher.getPages();// 总页数
        long size = pageTeacher.getSize();// 每页记录数
        long total = pageTeacher.getTotal();// 总记录数
        boolean hasNext = pageTeacher.hasNext();// 下一页
        boolean hasPrevious = pageTeacher.hasPrevious();// 上一页

        // 把分页数据获取出来，放到map集合中
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }
}
