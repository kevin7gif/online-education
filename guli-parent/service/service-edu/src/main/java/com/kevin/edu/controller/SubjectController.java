package com.kevin.edu.controller;


import com.kevin.edu.entity.subject.OneSubject;
import com.kevin.edu.service.SubjectService;
import com.kevin.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author kevin
 * @since 2023-07-26
 */
@RestController
@CrossOrigin
@Api(tags = "课程分类管理")
@RequestMapping("/eduservice/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    /**
     * 添加课程分类
     * 获取上传过来的文件，把文件内容读取出来
     * @param file 上传过来的文件
     * @return R
     */
    @ApiOperation(value = "Excel批量导入")
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file){
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    /**
     * 课程分类列表（树形）
     * @return
     */
    @ApiOperation(value = "课程数据列表")
    @GetMapping("/getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list=subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }



}

