package com.kevin.oss.controller;

import com.kevin.oss.service.OssService;
import com.kevin.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-07-26 11:00
 */
@RestController
@RequestMapping("/eduoss/fileoss")
@Api(tags = "阿里云文件管理")
public class OssController {

    @Autowired
    private OssService ossService;

    /**
     * 上传头像的方法
     * @param file 上传的文件
     * @return 返回R对象
     */
    @ApiOperation(value = "上传头像")
    @PostMapping
    public R uploadOssFile(MultipartFile file){
        // 获取上传文件 MultipartFile
        // 返回上传到oss的路径
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }
}
