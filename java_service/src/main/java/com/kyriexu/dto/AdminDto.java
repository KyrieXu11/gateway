package com.kyriexu.dto;

import javax.validation.constraints.NotBlank;

/**
 * @author KyrieXu
 * @since 2021/3/23 13:00
 **/
public class AdminDto {
    @NotBlank(message = "用户名不能为空")
    private String username;

    private String password;

    public AdminDto() {
    }

    public AdminDto(@NotBlank(message = "用户名不能为空") String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AdminDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
