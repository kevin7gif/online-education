package com.kevin.order.service.impl;

import com.kevin.order.client.EduClient;
import com.kevin.order.client.UcenterClient;
import com.kevin.order.entity.Order;
import com.kevin.order.mapper.OrderMapper;
import com.kevin.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevin.order.utils.OrderNoUtil;
import com.kevin.utils.ordervo.CourseWebVoOrder;
import com.kevin.utils.ordervo.UcenterMemberOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author kevin
 * @since 2023-08-09
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;

    // 生成订单的方法
    @Override
    public String createOrders(String courseId, String memberIdByJwtToken) {
        // 远程调用获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberIdByJwtToken);
        // 远程调用获取课程信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        // 创建Order对象，向order对象里面设置需要数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());// 订单号
        order.setCourseId(courseId);// 课程id
        order.setCourseTitle(courseInfoOrder.getTitle());// 课程标题
        order.setCourseCover(courseInfoOrder.getCover());// 课程封面
        order.setTeacherName(courseInfoOrder.getTeacherName());// 讲师名称
        order.setTotalFee(courseInfoOrder.getPrice());// 课程价格
        order.setMemberId(userInfoOrder.getId());// 会员id
        order.setMobile(userInfoOrder.getMobile());// 会员昵称
        order.setNickname(userInfoOrder.getNickname());// 会员昵称
        order.setStatus(0);// 订单状态（0：未支付 1：已支付）
        order.setPayType(1);// 支付类型，微信1
        baseMapper.insert(order);

        // 返回订单号
        return order.getOrderNo();
    }
}
