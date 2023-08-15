package com.kevin.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kevin.base.exception.GuliException;
import com.kevin.edu.entity.Course;
import com.kevin.edu.entity.CourseDescription;
import com.kevin.edu.mapper.CourseMapper;
import com.kevin.edu.service.ChapterService;
import com.kevin.edu.service.CourseDescriptionService;
import com.kevin.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevin.edu.service.VideoService;
import com.kevin.edu.vo.CourseFrontVO;
import com.kevin.edu.vo.CourseInfoVO;
import com.kevin.edu.vo.CoursePublishVO;
import com.kevin.edu.vo.CourseWebVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author kevin
 * @since 2023-07-28
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;// 课程简介
    @Autowired
    private VideoService videoService;// 小节
    @Autowired
    private ChapterService chapterService;// 章节

    // 添加课程基本信息的方法
    @Override
    @Transactional
    public String saveCourseInfo(CourseInfoVO courseInfoVO) {
        // 1. 向课程表添加课程基本信息
        // CourseInfoVO对象转换为Course对象
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVO, course);
        int insert = baseMapper.insert(course);

        if(insert==0){
            // 添加失败
            throw new GuliException(20001, "添加课程信息失败");
        }

        // 获取添加之后的课程id
        String cid=course.getId();

        // 向课程简介表添加课程简介
        CourseDescription courseDescription = new CourseDescription();
        // 课程id和课程简介id一致
        courseDescription.setId(cid);
        courseDescription.setDescription(courseInfoVO.getDescription());
        courseDescriptionService.save(courseDescription);

        return cid;
    }

    // 根据课程id查询课程基本信息
    @Override
    public CourseInfoVO getCourseInfo(String courseId) {
        // 1. 查询课程表
        Course course = baseMapper.selectById(courseId);
        CourseInfoVO courseInfoVO = new CourseInfoVO();
        BeanUtils.copyProperties(course, courseInfoVO);

        // 2. 查询描述表
        CourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVO.setDescription(courseDescription.getDescription());

        return courseInfoVO;
    }

    // 修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVO courseInfoVO) {
        // 1. 修改课程表
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVO, course);
        int update = baseMapper.updateById(course);
        if(update==0){
            throw new GuliException(20001, "修改课程信息失败");
        }

        // 2. 修改描述表
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(courseInfoVO.getId());
        courseDescription.setDescription(courseInfoVO.getDescription());
        courseDescriptionService.updateById(courseDescription);

    }

    // 根据课程id查询课程确认信息
    @Override
    public CoursePublishVO getPublishCourseInfo(String courseId) {
        CoursePublishVO publishCourseInfo = baseMapper.getPublishCourseInfo(courseId);
        return publishCourseInfo;
    }

    // 删除课程
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeCourse(String courseId) {
        // 1. 根据课程id删除小节
        videoService.removeVideoByCourseId(courseId);
        // 2. 根据课程id删除章节
        chapterService.removeChapterByCourseId(courseId);
        // 3. 根据课程id删除描述
        // 课程描述表里的id和课程表里的id是一个，所以可以直接删除
        courseDescriptionService.removeById(courseId);
        // 4. 根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if(result==0){
            throw new GuliException(20001, "删除失败");
        }
    }

    // 条件查询带分页查询课程
    @Override
    public Map<String, Object> getCourseFrontList(Page<Course> pageCourse, CourseFrontVO courseFrontVO) {
        // 根据讲师id查询所讲课程
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        // 判断条件值是否为空，不为空拼接
        if(!StringUtils.isEmpty(courseFrontVO.getSubjectParentId())){// 一级分类
            wrapper.eq("subject_parent_id", courseFrontVO.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVO.getSubjectId())){// 二级分类
            wrapper.eq("subject_id", courseFrontVO.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVO.getBuyCountSort())){// 关注度
            wrapper.orderByDesc("buy_count");
        }
        if(!StringUtils.isEmpty(courseFrontVO.getGmtCreateSort())){// 最新
            wrapper.orderByDesc("gmt_create");
        }
        if(!StringUtils.isEmpty(courseFrontVO.getPriceSort())){// 价格
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(pageCourse, wrapper);

        List<Course> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();// 下一页
        boolean hasPrevious = pageCourse.hasPrevious();// 上一页

        // 把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);// 当前页
        map.put("pages", pages);// 总页数
        map.put("size", size);// 每页记录数
        map.put("total", total);// 总记录数
        map.put("hasNext", hasNext);// 下一页
        map.put("hasPrevious", hasPrevious);// 上一页

        // map返回
        return map;
    }

    // 根据课程id，编写sql语句查询课程信息
    @Override
    public CourseWebVO getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
