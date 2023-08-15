package com.kevin.edu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-08-01 9:58
 */
@Data
public class CourseQueryVO {
    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程状态 Draft未发布  Normal已发布")
    private String status;
}
