package com.kevin.edu.service;

import com.kevin.edu.entity.subject.OneSubject;
import com.kevin.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author kevin
 * @since 2023-07-26
 */
public interface SubjectService extends IService<Subject> {

    // 添加课程分类
    void saveSubject(MultipartFile file,SubjectService subjectService);

    // 课程分类列表（树形）
    List<OneSubject> getAllOneTwoSubject();
}
