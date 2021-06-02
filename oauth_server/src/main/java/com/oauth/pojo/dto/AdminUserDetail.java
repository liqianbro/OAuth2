package com.oauth.pojo.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author lee
 * @date 2021/5/24 3:10 下午
 */
@Data
public class AdminUserDetail implements UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 登录账号
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 职务
     */
    private String postion;

    /**
     * 性别(0-默认未知,1-男,2-女)
     */
    private Integer sex;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 所属机构
     */
    private String orgCode;

    /**
     * 状态(0-正常,1-冻结)
     */
    private Integer status;

    /**
     * 删除状态(0-正常,1-已删除)
     */
    private Integer delFlag;

    /**
     * 工号，唯一键
     */
    private String workNo;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private String updateTime;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
