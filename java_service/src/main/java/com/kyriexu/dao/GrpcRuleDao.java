package com.kyriexu.dao;

import com.kyriexu.model.GrpcRule;
import org.apache.ibatis.annotations.Param;

/**
 * @author KyrieXu
 * @since 2021/3/24 14:49
 **/
public interface GrpcRuleDao {
    GrpcRule get(@Param("serviceId") Long serviceId);
}
