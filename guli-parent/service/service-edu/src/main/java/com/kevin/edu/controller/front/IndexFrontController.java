package com.kevin.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kevin.edu.entity.Course;
import com.kevin.edu.entity.Teacher;
import com.kevin.edu.service.CourseService;
import com.kevin.edu.service.TeacherService;
import com.kevin.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-08-03 8:27
 */
@RestController
@Api(tags = "前台首页")
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;

    /**
     * 查询前8条热门课程，查询前4条名师
     * @return
     */
    @ApiOperation(value = "查询前8条热门课程，查询前4条名师")
    @GetMapping ("/index")
    @Cacheable(value = "index",key = "'teacher_course_list'")// 通过redis缓存
    public R index(){
        //查询前8条热门课程
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<Course> courseList = courseService.list(wrapper);

        //查询前4条名师
        QueryWrapper<Teacher> wrapper1 = new QueryWrapper<>();
        wrapper1.orderByDesc("id");
        wrapper1.last("limit 4");
        List<Teacher> teacherList = teacherService.list(wrapper1);

        //返回两个list集合，一个是课程，一个是讲师
        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
