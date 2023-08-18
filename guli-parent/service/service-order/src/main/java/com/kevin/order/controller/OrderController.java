package com.kevin.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kevin.order.entity.Order;
import com.kevin.order.service.OrderService;
import com.kevin.utils.JwtUtils;
import com.kevin.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author kevin
 * @since 2023-08-09
 */
@RestController
@RequestMapping("/eduorder/order")
@Api(tags = "订单管理")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 生成订单的方法
     * @param courseId
     * @param request
     * @return
     */
    @ApiOperation("生成订单的方法")
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request){
        String orderNo = orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId",orderNo);
    }

    /**
     * 根据订单id查询订单信息
     * @param orderId
     * @return
     */
    @ApiOperation("根据订单id查询订单信息")
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("order_no",orderId);
        Order one = orderService.getOne(wrapper);
        return R.ok().data("item",one);
    }

    /**
     * 根据课程id和用户id查询订单表中订单状态
     * @param memberId
     * @param courseId
     * @return
     */
    @ApiOperation("根据课程id和用户id查询订单表中订单状态")
    @GetMapping("isBuyCourse/{memberId}/{courseId}")
    public boolean isBuyCourse(@PathVariable String memberId,@PathVariable String courseId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",memberId);
        wrapper.eq("course_id",courseId);
        wrapper.eq("status",1);// 1代表已经支付
        int count = orderService.count(wrapper);
        if(count>0){ // 已经支付
            return true;
        }else{
            return false;
        }
    }

}

