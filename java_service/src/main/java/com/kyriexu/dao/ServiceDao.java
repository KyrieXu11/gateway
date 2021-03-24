package com.kyriexu.dao;

import com.kyriexu.model.ServiceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/3/23 20:16
 **/
public interface ServiceDao {

    List<ServiceInfo> getServiceInfoList(@Param("page") int page, @Param("size") int size);

    ServiceInfo get(@Param("id") Long id);

    int getTotal(@Param("size") int size);

    ServiceInfo getByServiceName(@Param("serviceName") String serviceName);

    int saveServiceInfo(ServiceInfo info);
}
