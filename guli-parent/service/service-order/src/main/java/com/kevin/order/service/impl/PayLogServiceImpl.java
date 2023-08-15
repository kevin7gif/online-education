package com.kevin.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.kevin.base.exception.GuliException;
import com.kevin.order.entity.Order;
import com.kevin.order.entity.PayLog;
import com.kevin.order.mapper.PayLogMapper;
import com.kevin.order.service.OrderService;
import com.kevin.order.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevin.order.utils.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author kevin
 * @since 2023-08-09
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    @Autowired
    private OrderService orderService;

    // 生成微信支付二维码接口
    @Override
    public Map createNative(String orderNo) {
        try {
            // 1 根据订单号查询订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderNo);
            Order order = orderService.getOne(wrapper);

            // 2 使用map设置生成二维码需要参数
            Map map = new HashMap<>();
            map.put("appid", "wx74862e0dfcf69954");//微信id
            map.put("mch_id", "1558950191");//商户号
            map.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
            map.put("body", order.getCourseTitle());//课程标题
            map.put("out_trade_no", orderNo);//订单号
            map.put("total_fee", order.getTotalFee().multiply(new java.math.BigDecimal("100")).longValue() + "");//订单金额，单位是分
            map.put("spbill_create_ip", "127.0.0.1");//支付ip地址
            map.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");//回调地址
            map.put("trade_type", "NATIVE");//支付类型

            // 3 发送httpclient请求，传递参数xml格式，微信支付提供的固定的地址
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            // 设置xml格式的参数
            client.setXmlParam(WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));//商户key
            // 设置提交方式
            client.setHttps(true);
            // 执行请求发送
            client.post();

            // 4 得到发送请求返回结果
            // 返回内容，是使用xml格式返回
            String xml = client.getContent();
            // 把xml格式转换map集合，把map集合返回
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            // 5 封装返回结果集
            Map m = new HashMap<>();
            m.put("out_trade_no", orderNo);
            m.put("course_id", order.getCourseId());
            m.put("total_fee", order.getTotalFee());
            m.put("result_code", resultMap.get("result_code"));//返回二维码操作状态码
            m.put("code_url", resultMap.get("code_url"));//二维码地址
            return m;
        } catch (Exception e) {
            throw new GuliException(20001, "生成二维码失败");
        }
    }

    // 根据订单号查询订单支付状态
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            // 1 封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");//微信id
            m.put("mch_id", "1558950191");//商户号
            m.put("out_trade_no", orderNo);//订单号
            m.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
            // 2 设置请求内容
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            // 设置xml格式的参数
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));//商户key
            // 设置提交方式
            client.setHttps(true);
            // 执行请求发送
            client.post();
            // 3 得到请求返回内容
            String xml = client.getContent();
            // 4 转换成map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            // 5 返回
            return resultMap;

        } catch (Exception e) {
            throw new GuliException(20001, "查询订单支付状态失败");
        }
    }

    // 添加记录到支付表，更新订单表订单状态
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        // 从map中获取订单号
        String orderNo = map.get("out_trade_no");
        // 1 根据订单号查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);
        // 2 更新订单表订单状态
        if (order.getStatus().intValue() == 1) {
            return;
        }
        order.setStatus(1);//1代表已经支付
        orderService.updateById(order);

        // 3 向支付表添加支付记录
        PayLog payLog = new PayLog();
        payLog.setOrderNo(order.getOrderNo());//订单号
        payLog.setPayTime(new java.util.Date());//支付时间
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));//流水号
        payLog.setAttr(JSONObject.toJSONString(map));//其他属性
        baseMapper.insert(payLog);//插入到支付日志表
    }
}
