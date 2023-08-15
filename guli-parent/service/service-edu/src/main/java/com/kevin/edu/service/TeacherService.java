package com.kevin.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kevin.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author kevin
 * @since 2023-07-20
 */
public interface TeacherService extends IService<Teacher> {

    // 分页查询讲师
    Map<String, Object> getTeacherFrontList(Page<Teacher> pageTeacher);
}
