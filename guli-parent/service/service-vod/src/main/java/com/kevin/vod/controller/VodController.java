package com.kevin.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.kevin.base.exception.GuliException;
import com.kevin.utils.R;
import com.kevin.vod.service.VodService;
import com.kevin.vod.utils.ConstantVodUtils;
import com.kevin.vod.utils.InitVodClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-08-01 16:07
 */
@RestController
@RequestMapping("/eduvod/video")
@Api(tags = "阿里云视频点播")
public class VodController {

    @Autowired
    private VodService vodService;

    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    @ApiOperation("上传视频到阿里云")
    @PostMapping("/uploadAlyVideo")
    public R uploadAlyVideo(MultipartFile file) {
        // 返回上传视频id
        String videoId=vodService.uploadAlyVideo(file);
        return R.ok().data("videoId",videoId);
    }

    /**
     * 根据视频id删除阿里云视频
     * @param id
     * @return
     */
    @ApiOperation("根据视频id删除阿里云视频")
    @DeleteMapping ("/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id) {
        try {
            // 初始化对象
            DefaultAcsClient client= InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建删除视频request对象
            DeleteVideoRequest request=new DeleteVideoRequest();
            // 向request设置视频id
            request.setVideoIds(id);
            // 调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return R.ok();
        }catch (Exception e) {
            throw new GuliException(20001,"删除视频失败");
        }
    }

    /**
     * 删除多个阿里云视频的方法
     * @param videoIdList
     * @return
     */
    @ApiOperation("删除多个阿里云视频的方法")
    @DeleteMapping("/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreAlyVideo(videoIdList);
        return R.ok();
    }

    /**
     * 根据视频id获取视频凭证
     * @param id
     * @return
     */
    @ApiOperation("根据视频id获取视频凭证")
    @GetMapping("/getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            // 创建初始化对象
            DefaultAcsClient client=InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建获取凭证request和response对象
            GetVideoPlayAuthRequest request=new GetVideoPlayAuthRequest();
            // 向request设置视频id
            request.setVideoId(id);
            // 调用方法得到凭证
            GetVideoPlayAuthResponse response=client.getAcsResponse(request);
            String playAuth=response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        }catch (Exception e) {
            throw new GuliException(20001,"获取凭证失败");
        }
    }
}
