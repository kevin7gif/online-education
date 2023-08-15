package com.kevin.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kevin.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kevin.edu.vo.CourseFrontVO;
import com.kevin.edu.vo.CourseInfoVO;
import com.kevin.edu.vo.CoursePublishVO;
import com.kevin.edu.vo.CourseWebVO;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author kevin
 * @since 2023-07-28
 */
public interface CourseService extends IService<Course> {

    // 添加课程基本信息的方法
    String saveCourseInfo(CourseInfoVO courseInfoVO);

    // 根据课程id查询课程基本信息
    CourseInfoVO getCourseInfo(String courseId);

    // 修改课程信息
    void updateCourseInfo(CourseInfoVO courseInfoVO);

    // 根据课程id查询课程确认信息
    CoursePublishVO getPublishCourseInfo(String courseId);

    // 删除课程
    void removeCourse(String courseId);

    // 条件查询带分页查询课程
    Map<String, Object> getCourseFrontList(Page<Course> pageCourse, CourseFrontVO courseFrontVO);

    // 课程详情的方法
    CourseWebVO getBaseCourseInfo(String courseId);
}
