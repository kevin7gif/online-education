package com.kevin.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.junit.Test;

/**
 * 在阿里云中根据视频id获取视频地址
 * @author kevin
 * @version 1.0
 * @date 2023-08-01 14:57
 */
public class TestVod {
    public static void main(String[] args) {
        String accessKeyId = "yourKeyId";
        String accessKeySecret = "yourKeySecret";

        String title = "test for uploadPlay";   //上传之后文件名称
        String fileName = "E:\\OneDrive\\图片\\大学\\TG-2021-12-11-213933866.mp4";  //本地文件路径和名称
        //上传视频的方法
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);

        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    // 根据视频id获取视频播放凭证
    public static void getPlayAuth(){
        // 创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("yourKeyId", "yourKeySecret");
        // 创建获取视频凭证的request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        // 向request中设置视频id
        request.setVideoId("a1bc1f70303371eeac856732b68e0102");
        // 调用初始化方法得到凭证
        try {
            response = client.getAcsResponse(request);
            System.out.println("playAuth: " + response.getPlayAuth());
        } catch (Exception e) {
            System.out.println("ErrorMessage = " + e.getLocalizedMessage());
        }
    }

    // 根据视频id获取视频播放地址
    public static void getPlayUrl() {
        // 创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("yourKeyId", "yourKeySecret");

        // 创建获取视频地址request和response
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        GetPlayInfoRequest request = new GetPlayInfoRequest();

        // 向request对象里面设置视频id
        request.setVideoId("a1bc1f70303371eeac856732b68e0102");

        // 调用初始化对象里面的方法，传递request，获取数据
        try {
            response = client.getAcsResponse(request);
            System.out.println("PlayInfo: " + response.getPlayInfoList());
            // 播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : response.getPlayInfoList()) {
                System.out.println("PlayInfo.PlayURL = " + playInfo.getPlayURL());
            }
            // Base信息
            System.out.println("VideoBase.Title = " + response.getVideoBase().getTitle());
        } catch (Exception e) {
            System.out.println("ErrorMessage = " + e.getLocalizedMessage());
        }
    }

    // 上传视频
    public static void uploadPlay(){
        String accessKeyId = "yourKeyId";
        String accessKeySecret = "yourKeySecret";

        String title = "test for uploadPlay";   //上传之后文件名称
        String fileName = "E:\\OneDrive\\图片\\大学\\TG-2021-12-11-213933866.mp4";  //本地文件路径和名称
        //上传视频的方法
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);

        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

}
