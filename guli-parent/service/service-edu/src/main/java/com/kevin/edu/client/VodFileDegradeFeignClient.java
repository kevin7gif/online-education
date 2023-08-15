package com.kevin.edu.client;

import com.kevin.utils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-08-02 11:33
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{

    // 出错之后会执行
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频出错了");
    }

    // 出错之后会执行
    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了");
    }
}
