package com.oauth.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 管理端用户表
 * </p>
 *
 * @Author Lee
 * @since 2021-03-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("admin_user")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AdminUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 登录账号
     */
    @TableField("username")
    private String username;

    /**
     * 真实姓名
     */
    @TableField("realname")
    private String realname;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 生日
     */
    @TableField("birthday")
    private String birthday;

    /**
     * 职务
     */
    @TableField("postion")
    private String postion;

    /**
     * 性别(0-默认未知,1-男,2-女)
     */
    @TableField("sex")
    private Integer sex;

    /**
     * 电子邮件
     */
    @TableField("email")
    private String email;

    /**
     * 电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 所属机构
     */
    @TableField("org_code")
    private String orgCode;

    /**
     * 状态(0-正常,1-冻结)
     */
    @TableField("status")
    private Integer status;

    /**
     * 删除状态(0-正常,1-已删除)
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 工号，唯一键
     */
    @TableField("work_no")
    private String workNo;

    /**
     * 创建人
     */
    @TableField("create_user")
    private String createUser;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private String createTime;

    /**
     * 更新人
     */
    @TableField("update_user")
    private String updateUser;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private String updateTime;


}
