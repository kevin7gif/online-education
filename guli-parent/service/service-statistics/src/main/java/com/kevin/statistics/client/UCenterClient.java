package com.kevin.statistics.client;

import com.kevin.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-ucenter")
@Component
public interface UCenterClient {

    @GetMapping("/educenter/member/countRegister/{day}")
    @ApiOperation("统计某一天的注册人数")
    public R countRegister(@PathVariable("day") String day);
}
