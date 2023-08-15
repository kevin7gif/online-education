package com.kevin.ucenter.controller;


import com.kevin.ucenter.entity.Member;
import com.kevin.ucenter.service.MemberService;
import com.kevin.ucenter.vo.RegisterVO;
import com.kevin.utils.JwtUtils;
import com.kevin.utils.R;
import com.kevin.utils.ordervo.UcenterMemberOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author kevin
 * @since 2023-08-05
 */
@RestController
@RequestMapping("/educenter/member")
@Api(tags = "会员管理")
@CrossOrigin
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     *  登录
     * @param member
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("会员登录")
    public R loginUser(@RequestBody Member member) {
        //调用service方法实现登录
        //返回token值，使用jwt生成
        String token = memberService.login(member);
        return R.ok().data("token", token);
    }

    /**
     * 注册
     * @param registerVO
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("会员注册")
    public R registerUser(@RequestBody RegisterVO registerVO) {
        memberService.register(registerVO);
        return R.ok();
    }

    /**
     * 根据token获取用户信息
     * @param request
     * @return
     */
    @GetMapping("/getMemberInfo")
    @ApiOperation("根据token获取用户信息")
    public R getMemberInfo(HttpServletRequest request) {
       // 调用jwt工具类的方法，根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 查询数据库，根据用户id获取用户信息
        Member member = memberService.getById(memberId);
        return R.ok().data("userInfo", member);
    }

    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
    @PostMapping("/getUserInfoOrder/{id}")
    @ApiOperation("根据用户id获取用户信息")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {
        Member member = memberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        // 把member里面的值复制到ucenterMemberOrder里面
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    /**
     * 统计某一天的注册人数
     * @param day
     * @return
     */
    @GetMapping("/countRegister/{day}")
    @ApiOperation("统计某一天的注册人数")
    public R countRegister(@PathVariable String day) {
        Integer count = memberService.countRegisterDay(day);
        return R.ok().data("countRegister", count);
    }

}

