package com.kevin.edu.mapper;

import com.kevin.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kevin.edu.vo.CoursePublishVO;
import com.kevin.edu.vo.CourseWebVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author kevin
 * @since 2023-07-28
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    // 根据课程id，编写sql语句查询课程确认信息
    public CoursePublishVO getPublishCourseInfo(String courseId);

    // 根据课程id，编写sql语句查询课程信息
    CourseWebVO getBaseCourseInfo(String courseId);
}
