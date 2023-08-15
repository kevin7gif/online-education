package com.kevin.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kevin.edu.entity.Course;
import com.kevin.edu.entity.Teacher;
import com.kevin.edu.service.CourseService;
import com.kevin.edu.service.TeacherService;
import com.kevin.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-08-07 21:46
 */
@RestController
@RequestMapping("/eduservice/teacherfront")
@Api(tags = "前台讲师管理")
@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;

    /**
     * 分页查询讲师
     * @return
     */
    @PostMapping("/getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit) {
        Page<Teacher> pageTeacher = new Page<>(page, limit);
        Map<String,Object> map = teacherService.getTeacherFrontList(pageTeacher);
        // 返回分页所有数据
        return R.ok().data(map);
    }

    /**
     * 根据讲师id查询讲师详情和所讲课程
     * @param teacherId
     * @return
     */
    @ApiOperation(value = "根据讲师id查询讲师详情和所讲课程")
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId) {
        // 根据讲师id查询讲师基本信息
        Teacher teacher = teacherService.getById(teacherId);
        // 根据讲师id查询讲师所讲课程
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<Course> courseList = courseService.list(wrapper);
        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }
}
