package com.kevin.order.controller;


import com.kevin.order.service.PayLogService;
import com.kevin.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author kevin
 * @since 2023-08-09
 */
@RestController
@RequestMapping("/eduorder/paylog")
@Api(tags = "支付日志管理")
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    /**
     * 生成微信支付二维码接口
     * @param orderNo
     * @return
     */
    @ApiOperation(value = "生成微信支付二维码接口")
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        Map map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    /**
     * 查询订单支付状态
     * @param orderNo
     * @return
     */
    @ApiOperation(value = "查询订单支付状态")
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        if(map == null){
            return R.error().message("支付出错了");
        }
        // 如果返回map里面不为空，通过map获取订单状态
        if(map.get("trade_state").equals("SUCCESS")){
            // 添加记录到支付表，更新订单表订单状态
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }

}

