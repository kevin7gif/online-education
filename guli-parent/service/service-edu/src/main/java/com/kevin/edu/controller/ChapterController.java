package com.kevin.edu.controller;


import com.kevin.edu.entity.Chapter;
import com.kevin.edu.entity.chapter.ChapterVO;
import com.kevin.edu.service.ChapterService;
import com.kevin.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/eduservice/chapter")
@Api(tags = "课程章节管理")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    /**
     * 课程大纲列表，根据课程id进行查询
     * @param courseId 课程id
     * @return R
     */
    @ApiOperation(value = "课程大纲列表")
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVO> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo", list);
    }

    /**
     * 添加章节
     * @param chapter 章节对象
     * @return R
     */
    @ApiOperation(value = "添加章节")
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return R.ok();
    }

    /**
     * 根据章节id查询
     * @param chapterId 章节id
     * @return R
     */
    @ApiOperation(value = "根据章节id查询")
    @GetMapping("/getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        Chapter chapter = chapterService.getById(chapterId);
        return R.ok().data("chapter", chapter);
    }

    /**
     * 修改章节
     * @param chapter 章节对象
     * @return R
     */
    @ApiOperation(value = "修改章节")
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return R.ok();
    }

    /**
     * 删除章节
     * @param chapterId
     * @return
     */
    @ApiOperation(value = "删除章节")
    @DeleteMapping("/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = chapterService.deleteChapter(chapterId);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}

