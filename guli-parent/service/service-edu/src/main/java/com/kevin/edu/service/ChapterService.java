package com.kevin.edu.service;

import com.kevin.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kevin.edu.entity.chapter.ChapterVO;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author kevin
 * @since 2023-07-28
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVO> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
