package com.kyriexu.dao;

import com.kyriexu.model.LoadBalance;
import org.apache.ibatis.annotations.Param;

/**
 * @author KyrieXu
 * @since 2021/3/24 15:08
 **/
public interface LoadBalanceDao {

    LoadBalance get(@Param("serviceId") Long serviceId);

    int save(@Param("loadBalance") LoadBalance loadBalance);

}
