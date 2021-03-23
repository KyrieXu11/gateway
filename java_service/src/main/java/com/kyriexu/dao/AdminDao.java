package com.kyriexu.dao;

import com.kyriexu.model.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/3/23 14:32
 **/
public interface AdminDao {
    List<Admin> getAllAdmin();

    Admin getAdminByusrname(String username);

    int update(@Param("username") String username,@Param("newPass") String newPass);

    int add(@Param("admin") Admin admin);
}
