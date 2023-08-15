package com.kevin.msm.controller;

import com.kevin.utils.R;
import com.kevin.utils.ValidateCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-08-04 14:09
 */
@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
@Slf4j
@Api(tags = "短信服务")
public class MsmController {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 发送短信
     * @param phone
     * @return
     */
    @GetMapping("send/{phone}")
    @ApiOperation("发送短信")
    public R sendMsm(@PathVariable String phone){
        // 1.从redis获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)){
            log.info("手机号为："+phone+"的用户，验证码："+code+"可以继续使用!");
            return R.ok();
        }
        // 2.如果redis获取不到，进行短信发送
        code= ValidateCodeUtil.generateValidateCode(6);
        if(!StringUtils.isEmpty(code)){
            // 发送成功，把发送成功验证码放到redis里面
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            log.info("短信发送成功，验证码为：{}，有效期5分钟",code);
            return R.ok();
        }else {
            return R.error().message("短信发送失败");
        }
    }
}
