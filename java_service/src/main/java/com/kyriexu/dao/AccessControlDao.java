package com.kyriexu.dao;

import com.kyriexu.model.AccessControl;
import org.apache.ibatis.annotations.Param;

/**
 * @author KyrieXu
 * @since 2021/3/24 15:10
 **/
public interface AccessControlDao {

    AccessControl get(@Param("serviceId") Long serviceId);

    int save(@Param("accessControl") AccessControl accessControl);

    int update(@Param("accessControl") AccessControl accessControl);
}
