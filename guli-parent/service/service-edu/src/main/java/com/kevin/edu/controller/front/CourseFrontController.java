package com.kevin.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kevin.edu.client.OrdersClient;
import com.kevin.edu.entity.Course;
import com.kevin.edu.entity.chapter.ChapterVO;
import com.kevin.edu.service.ChapterService;
import com.kevin.edu.service.CourseService;
import com.kevin.edu.vo.CourseFrontVO;
import com.kevin.edu.vo.CourseWebVO;
import com.kevin.utils.JwtUtils;
import com.kevin.utils.R;
import com.kevin.utils.ordervo.CourseWebVoOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-08-08 11:49
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/coursefront")
@Api(tags = "前台课程管理")
public class CourseFrontController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private OrdersClient ordersClient;

    /**
     * 条件查询带分页查询课程
     * @param page
     * @param limit
     * @param courseFrontVO
     * @return
     */
    @ApiOperation(value = "条件查询带分页查询课程")
    @PostMapping("/getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVO courseFrontVO) {
        Page<Course> pageCourse = new Page<>(page, limit);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVO);
        // 返回分页所有数据
        return R.ok().data(map);
    }

    /**
     * 根据课程id查询课程详情信息
     * @param courseId
     * @return
     */
    @ApiOperation(value = "根据课程id查询课程详情信息")
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request) {
        // 根据课程id查询课程基本信息
        CourseWebVO courseWebVO = courseService.getBaseCourseInfo(courseId);
        // 根据课程id查询课程大纲
        List<ChapterVO> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        // 根据课程id和用户id查询当前课程是否已经支付过了
        if(JwtUtils.getMemberIdByJwtToken(request) == null) {
            return R.error().code(28004).message("请先登录！！！");
        }
        boolean buyCourse = ordersClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("courseWebVO",courseWebVO).data("chapterVideoList",chapterVideoList).data("isBuy",buyCourse);
    }

    /**
     * 根据课程id查询课程信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据课程id查询课程信息")
    @PostMapping("/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id) {
        CourseWebVO courseInfoOrder = courseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfoOrder,courseWebVoOrder);
        return courseWebVoOrder;
    }


}
