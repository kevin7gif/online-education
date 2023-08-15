package com.kevin.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-08-01 14:55
 */
public class InitObject {
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) {
        String regionId="cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile=DefaultProfile.getProfile(regionId,accessKeyId,accessKeySecret);
        DefaultAcsClient client=new DefaultAcsClient(profile);
        return client;
    }
}
