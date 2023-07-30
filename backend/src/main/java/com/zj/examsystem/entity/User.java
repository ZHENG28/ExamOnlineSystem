package com.zj.examsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    private String account;

    private String password;

    private String username;

    private Integer roleId;

    @TableField(select = false)
    private String roleName;

    @TableField(select = false)
    private String description;

    @TableField(select = false)
    private Integer clazzId;

    @TableField(select = false)
    private String clazzName;

    @TableField(select = false)
    private Integer majorId;

    @TableField(select = false)
    private String majorName;

    @TableField(select = false)
    private List<GrantedAuthority> authorities;

    public User(Integer userId, String account, String password, String username, Integer roleId) {
        this.userId = userId;
        this.account = account;
        this.password = password;
        this.username = username;
        this.roleId = roleId;
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
