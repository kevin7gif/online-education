package com.kevin.edu.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 前端返回的查询条件
 * @author kevin
 * @version 1.0
 * @date 2023-07-21 10:07
 */
@Data
public class TeacherQueryVO {
    @ApiModelProperty(value = "教师名称,模糊查询")
    private String name;
    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;
    @ApiModelProperty(value = "查询开始时间",example = "2023-07-21 10:10:10")
    private String begin;
    @ApiModelProperty(value = "查询结束时间",example = "2023-07-21 10:10:10")
    private String end;
}
