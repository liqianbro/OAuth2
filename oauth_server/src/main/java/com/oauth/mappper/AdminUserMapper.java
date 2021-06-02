package com.oauth.mappper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oauth.pojo.AdminUser;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 管理端用户表 Mapper 接口
 * </p>
 *
 * @Author Lee
 * @since 2021-03-12
 */
@Repository
public interface AdminUserMapper extends BaseMapper<AdminUser> {

}
