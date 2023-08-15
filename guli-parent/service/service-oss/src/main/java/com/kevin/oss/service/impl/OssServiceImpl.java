package com.kevin.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.kevin.oss.service.OssService;
import com.kevin.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-07-26 11:00
 */
@Service
public class OssServiceImpl implements OssService {

    // 上传头像到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // 工具类获取值
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        InputStream inputStream=null;
        OSS ossClient=null;
        String fileName=null;
        try {
            // 创建OSS实例
            ossClient=new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
            // 获取上传文件输入流
            inputStream= file.getInputStream();
            // 获取文件名称
            fileName = file.getOriginalFilename();

            // 在文件名称里添加随机唯一的值
            String uuid = java.util.UUID.randomUUID().toString().replaceAll("-","");
            fileName=uuid+fileName;
            // 把文件按照日期进行分类
            // 获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd");

            // 拼接文件名称
            fileName=datePath+"/"+fileName;//例如：2021/07/26/1.jpg


            // 上传文件到oss
            // 第一个参数：bucket名称 第二个参数：上传到oss文件路径和名称 /aa/bb/1.jpg 第三个参数：上传文件输入流
            ossClient.putObject(bucketName,fileName,inputStream);

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 关闭输入流和oss
            try {
                ossClient.shutdown();
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // 把上传之后的文件路径返回
        String url="https://"+bucketName+"."+endpoint+"/"+fileName;//拼接的结果 https://kevin7gif-guli-college.oss-cn-beijing.aliyuncs.com/16584.png
        return url;
    }
}
