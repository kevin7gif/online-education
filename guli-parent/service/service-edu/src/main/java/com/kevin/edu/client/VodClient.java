package com.kevin.edu.client;

import com.kevin.utils.R;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-08-02 8:52
 */
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)// 调用的服务名称
@Component
public interface VodClient {

    // 根据视频id删除阿里云视频
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id);

    // 删除多个阿里云视频的方法
    // 参数是多个视频id
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);

}
