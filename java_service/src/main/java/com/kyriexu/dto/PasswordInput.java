package com.kyriexu.dto;

import javax.validation.constraints.NotNull;

/**
 * @author KyrieXu
 * @since 2021/3/23 17:00
 **/
public class PasswordInput {
    @NotNull(message = "原来的密码不能为空")
    private String oldPass;

    @NotNull(message = "新密码不能为空")
    private String newPass;

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
