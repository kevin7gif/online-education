package com.kevin.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kevin.edu.entity.Teacher;
import com.kevin.edu.service.TeacherService;
import com.kevin.edu.vo.TeacherQueryVO;
import com.kevin.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author kevin
 * @since 2023-07-20
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 查询所有讲师
     * @return R
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public R findAllTeacher(){
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }

    /**
     * 逻辑删除
     * @param id 讲师ID
     * @return R
     */
    @ApiOperation(value = "根据讲师ID逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id",value = "讲师ID",required = true) @PathVariable("id") String id) {
        boolean flag = teacherService.removeById(id);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    /**
     * 分页查询讲师
     * @param current 当前页
     * @param limit 每页记录数
     * @return R
     */
    @ApiOperation(value = "分页查询讲师")
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable("current") long current,
                             @PathVariable("limit") long limit){
        //创建page对象
        Page<Teacher> teacherPage=new Page<>(current, limit);
        teacherService.page(teacherPage,null);

        long total = teacherPage.getTotal();//总记录数
        List<Teacher> records = teacherPage.getRecords();//数据list集合

        return R.ok().data("total",total).data("rows",records);//采用链式编程
    }

    /**
     * 条件查询带分页
     * @param current 当前页
     * @param limit 每页记录数
     * @param teacherQueryVO 条件对象
     * @return R
     */
    @ApiOperation(value = "条件查询带分页")
    @PostMapping ("/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current") long current,
                                  @PathVariable("limit") long limit,
                                  @RequestBody TeacherQueryVO teacherQueryVO
                                  ){
        //构建page对象
        Page<Teacher> teacherPage=new Page<>(current, limit);
        //构造条件
        QueryWrapper<Teacher> wrapper=new QueryWrapper<>();
        //多条件组合查询
        //mybatis学过 动态sql
        String name = teacherQueryVO.getName();
        Integer level = teacherQueryVO.getLevel();
        String begin = teacherQueryVO.getBegin();
        String end = teacherQueryVO.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(name)){
            //构建条件
            wrapper.like("name",name);//like模糊查询
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);//eq等于
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);//ge大于等于
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);//le小于等于
        }

        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        teacherService.page(teacherPage,wrapper);

        long total = teacherPage.getTotal();//总记录数
        List<Teacher> records = teacherPage.getRecords();//数据list集合
        return R.ok().data("total",total).data("rows",records);
    }

    /**
     * 添加讲师
     * @param teacher
     * @return
     */
    @ApiOperation(value = "添加讲师")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody Teacher teacher){
        boolean save = teacherService.save(teacher);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    /**
     * 根据讲师ID查询
     * @param id
     * @return
     */
    @ApiOperation(value = "根据讲师ID查询")
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable("id") String id){
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

    /**
     * 修改讲师
     * @param teacher
     * @return
     */
    @ApiOperation(value = "修改讲师")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody Teacher teacher){
        boolean flag = teacherService.updateById(teacher);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

}

