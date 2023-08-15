package com.kevin.ucenter.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-08-05 9:35
 */
@Data
public class RegisterVO {
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "验证码")
    private String code;
}
