package com.kevin.edu.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程中的一级分类
 *
 * @author kevin
 * @version 1.0
 * @date 2023-07-27 22:19
 */
@Data
public class OneSubject {
    private String id;
    private String title;

    // 一级分类中有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
