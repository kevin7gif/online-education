package com.kevin.edu.vo;

import lombok.Data;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-07-31 15:21
 */
@Data
public class CoursePublishVO {
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;// 只用于显示
}
