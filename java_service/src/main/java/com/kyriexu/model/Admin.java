package com.kyriexu.model;

import com.kyriexu.common.utils.Constant;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author KyrieXu
 * @since 2021/3/23 11:32
 **/
public class Admin {
    @Min(value = 1, message = "检查id填写是否正确")
    private Integer id;
    @NotNull(message = "用户名不能为空")
    private String username;
    private String salt;
    private String password;
    @NotNull
    private Date updateAt;
    private Date createAt;
    private boolean deleted;

    public Admin() {
        this.salt = Constant.PASSWORD_SALT;
    }

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
        this.updateAt = new Date();
        this.createAt = new Date();
        this.deleted = false;
        this.salt = Constant.PASSWORD_SALT;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
