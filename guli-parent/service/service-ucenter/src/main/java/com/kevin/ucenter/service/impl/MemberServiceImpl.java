package com.kevin.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kevin.base.exception.GuliException;
import com.kevin.ucenter.entity.Member;
import com.kevin.ucenter.mapper.MemberMapper;
import com.kevin.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevin.ucenter.vo.RegisterVO;
import com.kevin.utils.JwtUtils;
import com.kevin.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author kevin
 * @since 2023-08-05
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 注册
    @Override
    public String login(Member member) {
        // 获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();
        // 判断手机号是否为空
        if (mobile == null || password == null) {
            throw new GuliException(20001, "登录失败");
        }
        // 判断手机号是否正确
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Member mobileMember = baseMapper.selectOne(wrapper);
        // 判断查询对象是否为空
        if (mobileMember == null) { // 没有这个手机号
            throw new GuliException(20001, "登录失败");
        }
        // 判断密码,加密方式 MD5
        // 因为存储的是加密后的密码，所以需要将输入的密码加密后再进行比较
        // 加密后的密码
        if (!mobileMember.getPassword().equals(MD5.encrypt(password))) {
            throw new GuliException(20001, "登录失败");
        }
        // 判断用户是否禁用
        if (mobileMember.getIsDisabled()) {
            throw new GuliException(20001, "登录失败");
        }
        // 登录成功
        // 生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        return jwtToken;
    }

    // 注册
    @Override
    public void register(RegisterVO registerVO) {
        // 获取注册的数据
        String code = registerVO.getCode(); // 验证码
        String mobile = registerVO.getMobile(); // 手机号
        String nickname = registerVO.getNickname(); // 昵称
        String password = registerVO.getPassword(); // 密码

        // 判断手机号和密码是否为空
        if (mobile == null || password == null || nickname == null || code == null) {
            throw new GuliException(20001, "注册失败");
        }
        // 判断验证码
        // 获取redis中的验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new GuliException(20001, "验证码错误或已过期");
        }
        // 判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new GuliException(20001, "该手机号已经注册过用户！！！");
        }
        // 数据添加数据库中
        Member member = new Member();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password)); // 密码需要加密
        member.setIsDisabled(false); // 用户不禁用
        member.setAvatar("https://kevin7gif-guli-college.oss-cn-beijing.aliyuncs.com/2023/07/26/16584.png");
        baseMapper.insert(member);
    }

    // 根据openid判断数据库是否存在相同微信数据
    @Override
    public Member getOpenIdMember(String openid) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        Member member = baseMapper.selectOne(wrapper);
        return member;
    }

    // 查询某一天的注册人数
    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
