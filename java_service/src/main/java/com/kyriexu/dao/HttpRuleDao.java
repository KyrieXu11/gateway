package com.kyriexu.dao;

import com.kyriexu.model.HttpRule;
import org.apache.ibatis.annotations.Param;

/**
 * @author KyrieXu
 * @since 2021/3/24 12:11
 **/
public interface HttpRuleDao {

    HttpRule get(@Param("serviceId") Long serviceId);

    HttpRule getByRuleTypeAndRule(@Param("ruleType") int ruleType, @Param("rule") String rule);

    int add(@Param("httpRule") HttpRule httpRule);

}
