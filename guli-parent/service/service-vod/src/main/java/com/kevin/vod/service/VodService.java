package com.kevin.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    // 上传视频到阿里云
    String uploadAlyVideo(MultipartFile file);

    // 根据视频id删除多个阿里云视频
    void removeMoreAlyVideo(List videoIdList);
}
