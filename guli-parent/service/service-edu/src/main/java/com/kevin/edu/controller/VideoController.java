package com.kevin.edu.controller;


import com.kevin.edu.client.VodClient;
import com.kevin.edu.entity.Video;
import com.kevin.edu.service.VideoService;
import com.kevin.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author kevin
 * @since 2023-07-28
 */
@RestController
@RequestMapping("/eduservice/video")
@Api(tags = "课程小结管理")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private VodClient vodClient;

    /**
     * 添加小结
     *
     * @param video
     * @return
     */
    @PostMapping("/addVideo")
    @ApiOperation("添加小结")
    public R addVideo(@RequestBody Video video) {
        videoService.save(video);
        return R.ok();
    }


    /**
     * 根据小结id删除小结
     * 删除小结的同时也要删除阿里云中的视频
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("根据id删除小结")
    public R deleteVideo(@PathVariable String id) {
        // 根据小结id获取视频id
        Video video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();

        // 判断小结中是否有视频id
        if (!StringUtils.isEmpty(videoSourceId)) {
            // 根据视频id删除阿里云视频
            R r = vodClient.removeAlyVideo(videoSourceId);
            if (r.getCode() == 20001) {
                throw new RuntimeException("删除视频失败，熔断器...");
            }
        }
        // 删除小结
        videoService.removeById(id);
        return R.ok();
    }

    /**
     * 修改小结
     *
     * @param video
     * @return
     */
    @PostMapping("/updateVideo")
    @ApiOperation("修改小结")
    public R updateVideo(@RequestBody Video video) {
        videoService.updateById(video);
        return R.ok();
    }

    /**
     * 根据id查询小结
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询小结")
    public R getVideoById(@PathVariable String id) {
        Video video = videoService.getById(id);
        return R.ok().data("video", video);
    }


}

