package com.kevin.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kevin.base.exception.GuliException;
import com.kevin.edu.entity.Chapter;
import com.kevin.edu.entity.Video;
import com.kevin.edu.entity.chapter.ChapterVO;
import com.kevin.edu.entity.chapter.VideoVO;
import com.kevin.edu.mapper.ChapterMapper;
import com.kevin.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevin.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author kevin
 * @since 2023-07-28
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    @Autowired
    private VideoService videoService;

    // 课程大纲列表，根据课程id进行查询
    @Override
    public List<ChapterVO> getChapterVideoByCourseId(String courseId) {
        // 1.根据课程id查询课程里面所有的章节
        QueryWrapper<Chapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<Chapter> chapterList = baseMapper.selectList(wrapperChapter);

        // 2.根据课程id查询课程里面所有的小节
        QueryWrapper<Video> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<Video> videoList = videoService.list(wrapperVideo);

        // 创建list集合，用于最终封装数据
        List<ChapterVO> finalChapterList = new ArrayList<>();

        // 3.遍历查询章节list集合进行封装
        for (int i = 0; i < chapterList.size(); i++) {
            // 每个章节
            Chapter chapter = chapterList.get(i);
            // Chapter对象复制到ChapterVO对象里面
            ChapterVO chapterVO = new ChapterVO();
            BeanUtils.copyProperties(chapter, chapterVO);
            // 把chapterVO放到最终list集合
            finalChapterList.add(chapterVO);

            // 创建list集合，用于封装章节的小节
            List<VideoVO> finalVideoList = new ArrayList<>();

            // 4.遍历查询小节list集合进行封装
            for (int m = 0; m < videoList.size(); m++) {
                // 得到每个小节
                Video video = videoList.get(m);
                // 判断：小结里面chapterid和章节里面的id是否相同
                if (video.getChapterId().equals(chapter.getId())) {
                    // 把video对象复制到videoVO里面
                    VideoVO videoVO = new VideoVO();
                    BeanUtils.copyProperties(video, videoVO);
                    // 把videoVO放到小节封装集合
                    finalVideoList.add(videoVO);
                }
            }

            // 把封装之后小节list集合，放到章节对象里面
            chapterVO.setChildren(finalVideoList);
        }
        return finalChapterList;
    }

    // 删除章节的方法
    @Override
    public boolean deleteChapter(String chapterId) {
        // 根据chapterid章节id查询小节表，如果查询数据，不进行删除
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = videoService.count(wrapper);// 查询小节表里面是否存在数据
        // 判断
        if (count > 0) { // 查询出小节，不进行删除
            throw new GuliException(20001, "不能删除");
        } else { // 不能查询数据，进行删除
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
    }

    // 根据课程id删除章节
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }


}
