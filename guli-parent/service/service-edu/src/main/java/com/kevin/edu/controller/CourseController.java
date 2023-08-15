package com.kevin.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kevin.edu.entity.Course;
import com.kevin.edu.service.CourseService;
import com.kevin.edu.vo.CourseInfoVO;
import com.kevin.edu.vo.CoursePublishVO;
import com.kevin.edu.vo.CourseQueryVO;
import com.kevin.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author kevin
 * @since 2023-07-28
 */
@RestController
@RequestMapping("/eduservice/course")
@Api(tags = "课程管理")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;

    /**
     * 添加课程信息
     * @param courseInfoVO
     * @return
     */
    @ApiOperation(value = "添加课程基本信息")
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVO courseInfoVO) {
        // 返回添加之后的课程id，为了后面添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVO);
        return R.ok().data("courseId", id);
    }

    /**
     * 根据课程id查询课程基本信息
     * @param courseId
     * @return
     */
    @GetMapping("/getCourseInfo/{courseId}")
    @ApiOperation(value = "根据课程id查询课程基本信息")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVO courseInfoVO = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVO", courseInfoVO);
    }

    /**
     * 修改课程信息
     *
     * @param courseInfoVO
     * @return
     */
    @PostMapping("/updateCourseInfo")
    @ApiOperation(value = "修改课程信息")
    public R updateCourseInfo(@RequestBody CourseInfoVO courseInfoVO) {
        courseService.updateCourseInfo(courseInfoVO);
        return R.ok();
    }

    /**
     * 根据课程id查询课程确认信息
     * @param courseId
     * @return
     */
    @GetMapping("/getPublishCourseInfo/{courseId}")
    @ApiOperation(value = "根据课程id查询课程确认信息")
    public R getPublishCourseInfo(@PathVariable String courseId) {
        CoursePublishVO coursePublishVO = courseService.getPublishCourseInfo(courseId);
        return R.ok().data("publishCourseInfo", coursePublishVO);
    }

    /**
     * 课程最终发布
     *
     * @param courseId
     * @return
     */
    @PostMapping("/publishCourse/{courseId}")
    @ApiOperation(value = "课程最终发布")
    public R publishCourse(@PathVariable String courseId) {
        Course course = new Course();
        course.setId(courseId);
        course.setStatus("Normal");// 设置课程发布状态
        courseService.updateById(course);
        return R.ok();
    }

    /**
     * 展示课程列表，条件查询带分页
     * @param current
     * @param limit
     * @param courseQueryVO
     * @return
     */
    @PostMapping("/pageCourseCondition/{current}/{limit}")
    @ApiOperation(value = "展示课程列表，条件查询带分页")
    public R pageCourseCondition(@PathVariable("current") long current,
                                 @PathVariable("limit") long limit,
                                 @RequestBody CourseQueryVO courseQueryVO) {
        // 创建page对象
        Page<Course> coursePage = new Page<>(current, limit);
        // 构造条件
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        String title = courseQueryVO.getTitle();
        String status = courseQueryVO.getStatus();
        // 判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(title)){
            // 模糊查询
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            // 精确查询
            wrapper.eq("status", status);
        }

        // 排序
        wrapper.orderByDesc("gmt_modified");

        // 调用方法实现分页查询
        courseService.page(coursePage, wrapper);

        long total=coursePage.getTotal();// 总记录数
        List<Course> records = coursePage.getRecords(); // 数据list集合
        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 删除课程
     * @param courseId
     * @return
     */
    @DeleteMapping("/deleteCourse/{courseId}")
    @ApiOperation(value = "删除课程")
    public R deleteCourse(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }

}

