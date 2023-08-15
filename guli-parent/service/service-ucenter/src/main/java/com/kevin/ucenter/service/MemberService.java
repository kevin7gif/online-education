package com.kevin.ucenter.service;

import com.kevin.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kevin.ucenter.vo.RegisterVO;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author kevin
 * @since 2023-08-05
 */
public interface MemberService extends IService<Member> {

    // 会员登录
    String login(Member member);

    // 会员注册
    void register(RegisterVO registerVO);

    // 根据openid判断数据库是否存在相同微信数据
    Member getOpenIdMember(String openid);

    // 查询某一天的注册人数
    Integer countRegisterDay(String day);
}
