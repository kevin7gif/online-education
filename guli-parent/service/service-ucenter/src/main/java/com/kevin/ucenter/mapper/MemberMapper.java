package com.kevin.ucenter.mapper;

import com.kevin.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.ManagedBean;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author kevin
 * @since 2023-08-05
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

    // 查询某一天的注册人数
    Integer countRegisterDay(String day);
}
