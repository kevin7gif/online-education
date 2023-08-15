package com.kevin.cms.service;

import com.kevin.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author kevin
 * @since 2023-08-02
 */
public interface CrmBannerService extends IService<CrmBanner> {

    // 1. 查询所有banner
    List<CrmBanner> selectAllBanner();
}
