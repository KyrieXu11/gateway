package com.kyriexu.dao;

import com.kyriexu.model.TcpRule;
import org.apache.ibatis.annotations.Param;

/**
 * @author KyrieXu
 * @since 2021/3/24 14:46
 **/
public interface TcpRuleDao {
    TcpRule get(@Param("serviceId") Long serviceId);

    int add(@Param("tcpRule") TcpRule tcpRule);

    int update(@Param("rule") TcpRule rule);
}
