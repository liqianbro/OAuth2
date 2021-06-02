package com.auth.service;

import com.auth.mappper.AdminUserMapper;
import com.auth.pojo.AdminUser;
import com.auth.pojo.dto.AdminUserDetail;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author lee
 * @date 2021/5/24 3:04 下午
 */
@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    AdminUserMapper adminUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUser selectAdminUser = adminUserMapper.selectOne(new QueryWrapper<AdminUser>().eq("username", username));
        if (null == selectAdminUser) {
            throw new UsernameNotFoundException("未找到该用户");
        }
        AdminUserDetail userDetail = new AdminUserDetail();
        BeanUtils.copyProperties(selectAdminUser, userDetail);
        return userDetail;
    }

}
