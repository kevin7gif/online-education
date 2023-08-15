package com.kevin.edu.service;

import com.kevin.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author kevin
 * @since 2023-07-28
 */
public interface VideoService extends IService<Video> {

    // 根据课程id删除小节
    void removeVideoByCourseId(String courseId);
}
