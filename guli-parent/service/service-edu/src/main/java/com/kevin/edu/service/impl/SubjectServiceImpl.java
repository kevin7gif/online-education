package com.kevin.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kevin.edu.entity.subject.OneSubject;
import com.kevin.edu.entity.Subject;
import com.kevin.edu.entity.subject.TwoSubject;
import com.kevin.edu.excel.SubjectData;
import com.kevin.edu.listener.SubjectExcelListener;
import com.kevin.edu.mapper.SubjectMapper;
import com.kevin.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author kevin
 * @since 2023-07-26
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public void saveSubject(MultipartFile file, SubjectService subjectService) {
        try {
            // 文件输入流
            InputStream in = file.getInputStream();
            // 调用方法进行读取
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        // 查询所有一级分类 parentid=0
        QueryWrapper<Subject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");// 等于0
        List<Subject> oneSubjectList = baseMapper.selectList(wrapperOne);

        // 查询所有二级分类 parentid!=0
        QueryWrapper<Subject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");// 不等于0
        List<Subject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        // 创建list集合，用于存储最终封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();
        // 封装一级分类
        // 对查询出来所有的一级分类list集合遍历，得到每一个一级分类对象，获取每个一级分类对象值
        // 封装到要求的list集合里面 List<OneSubject> finalSubjectList
        for (int i = 0; i < oneSubjectList.size(); i++) {
            // 得到oneSubjectList每个eduSubject对象
            Subject eduSubject = oneSubjectList.get(i);
            // 把eduSubject里面值获取出来，放到OneSubject对象里面
            // 多个OneSubject放到finalSubjectList里面
            OneSubject oneSubject = new OneSubject();
            // oneSubject.setId(eduSubject.getId());
            // oneSubject.setTitle(eduSubject.getTitle());
            // 也可以使用BeanUtils.copyProperties(eduSubject,oneSubject);
            BeanUtils.copyProperties(eduSubject, oneSubject);

            // 多个OneSubject放到finalSubjectList里面
            finalSubjectList.add(oneSubject);

            // 在一级分类循环遍历查询所有的二级分类
            // 创建list集合封装每个一级分类的二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            // 遍历二级分类list集合
            for (int m = 0; m < twoSubjectList.size(); m++) {
                // 获取每个二级分类
                Subject tSubject = twoSubjectList.get(m);
                // 判断二级分类parentid和一级分类id是否一样
                if (tSubject.getParentId().equals(eduSubject.getId())) {
                    // 把tSubject值复制到TwoSubject里面，放到twoFinalSubjectList里面
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject, twoSubject);
                    // 放到twoFinalSubjectList里面
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            // 把一级下面所有二级分类放到一级分类里面
            oneSubject.setChildren(twoFinalSubjectList);
        }
        return finalSubjectList;
    }
}
