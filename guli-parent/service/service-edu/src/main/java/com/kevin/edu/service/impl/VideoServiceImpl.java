package com.kevin.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kevin.edu.client.VodClient;
import com.kevin.edu.entity.Video;
import com.kevin.edu.mapper.VideoMapper;
import com.kevin.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author kevin
 * @since 2023-07-28
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    @Autowired
    private VodClient vodClient;

    // 根据课程id删除小节
    // TODO: 2021/8/1 后面这个方法要完善：删除小节的同时，把里面视频删除
    @Override
    public void removeVideoByCourseId(String courseId) {
        // 1. 根据课程id查询所有的视频id
        QueryWrapper<Video> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        // 只查询video_source_id字段
        wrapperVideo.select("video_source_id");
        List<Video> videos = baseMapper.selectList(wrapperVideo);

        // 2. List<Video> --> List<String>
        List<String> videoIds = videos.stream()
                .filter(video -> video.getVideoSourceId() != null)
                .map(Video::getVideoSourceId)
                .collect(Collectors.toList());

        // 3. 调用vodClient中的方法，删除多个阿里云视频
        if (videoIds.size() > 0) {// 防止videoIds为空，调用vodClient.deleteBatch方法报错
            vodClient.deleteBatch(videoIds);
        }


        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
