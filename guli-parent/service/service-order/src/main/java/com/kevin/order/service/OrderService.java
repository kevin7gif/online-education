package com.kevin.order.service;

import com.kevin.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author kevin
 * @since 2023-08-09
 */
public interface OrderService extends IService<Order> {

    // 生成订单的方法
    String createOrders(String courseId, String memberIdByJwtToken);
}
