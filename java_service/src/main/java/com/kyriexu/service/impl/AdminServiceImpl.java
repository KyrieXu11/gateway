package com.kyriexu.service.impl;

import com.kyriexu.dao.AdminDao;
import com.kyriexu.dto.AdminDto;
import com.kyriexu.dto.PasswordInput;
import com.kyriexu.exception.BaseException;
import com.kyriexu.exception.ResultStatus;
import com.kyriexu.model.Admin;
import com.kyriexu.service.AdminService;
import com.kyriexu.utils.Constant;
import com.kyriexu.utils.Utils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author KyrieXu
 * @since 2021/3/23 14:41
 **/
@Service
public class AdminServiceImpl implements AdminService {

    public static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AdminDao adminDao;

    @Override
    public boolean checkPassword(AdminDto adminDto) {
        if (Strings.isBlank(adminDto.getPassword())) {
            throw new BaseException(ResultStatus.ARGS_NULL_EXCEPTION);
        }
        Admin admin = getAdmin(adminDto.getUsername());
        String pass = Utils.getSaltyPass(admin.getSalt(), adminDto.getPassword());
        if (!admin.getPassword().equals(pass)) {
            throw new BaseException(ResultStatus.PASS_WRONG_EXCEPTION);
        }
        return true;
    }

    @Override
    public boolean changePass(PasswordInput passwordInput) {
        Object o = request.getSession().getAttribute(Constant.USER);
        if (Objects.isNull(o)) {
            throw new BaseException(ResultStatus.ADMIN_NOT_LOGIN_EXCEPTION);
        }
        AdminDto a = (AdminDto) o;
        String username = a.getUsername();
        AdminDto adminDto = new AdminDto(username, passwordInput.getOldPass());
        boolean b = checkPassword(adminDto);
        if (!b) {
            throw new BaseException(ResultStatus.OLD_PASS_WRONG_EXCEPTION);
        }
        Admin admin = getAdmin(username);
        String newPass = passwordInput.getNewPass();
        newPass = Utils.getSaltyPass(admin.getSalt(), newPass);
        int update = adminDao.update(username, newPass);
        return update > 0;
    }

    @Override
    public boolean register(AdminDto adminDto) {
        if (Strings.isBlank(adminDto.getPassword())) {
            throw new BaseException(ResultStatus.PASS_IS_NULL_EXCEPTION);
        }
        Admin admin = new Admin(adminDto.getUsername(), adminDto.getPassword());
        int res = adminDao.add(admin);
        return res > 0;
    }

    private Admin getAdmin(String username) {
        Admin admin = adminDao.getAdminByusrname(username);
        if (Objects.isNull(admin)) {
            throw new BaseException(ResultStatus.NO_SUCH_ADMIN_EXCEPTION);
        }
        return admin;
    }
}
