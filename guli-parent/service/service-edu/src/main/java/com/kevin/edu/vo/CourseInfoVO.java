package com.kevin.edu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-07-28 16:52
 */
@Data
public class CourseInfoVO {
    @ApiModelProperty(value = "课程ID")
    private String id;
    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;
    @ApiModelProperty(value = "课程专业ID")
    private String subjectId;
    @ApiModelProperty(value = "课程专业父级ID")
    private String subjectParentId;
    @ApiModelProperty(value = "课程标题")
    private String title;
    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;
    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;
    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;
    @ApiModelProperty(value = "课程简介")
    private String description;

}
