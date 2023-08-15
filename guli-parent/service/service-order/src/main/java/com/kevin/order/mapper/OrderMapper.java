package com.kevin.order.mapper;

import com.kevin.order.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author kevin
 * @since 2023-08-09
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
