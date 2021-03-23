package com.kyriexu.service;

import com.kyriexu.dto.AdminDto;
import com.kyriexu.dto.PasswordInput;

/**
 * @author KyrieXu
 * @since 2021/3/23 14:41
 **/
public interface AdminService {

    boolean checkPassword(AdminDto adminDto);

    boolean changePass(PasswordInput passwordInput);

    boolean register(AdminDto adminDto);
}
