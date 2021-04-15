package com.kyriexu.dao;

import com.kyriexu.dto.ServiceStatItemOutput;
import com.kyriexu.model.ServiceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/3/23 20:16
 **/
public interface ServiceDao {

    List<ServiceInfo> getServiceInfoList(@Param("page") int page, @Param("size") int size, @Param("query") String query);

    ServiceInfo get(@Param("id") Long id);

    int getTotal(@Param("query") String info);

    ServiceInfo getByServiceName(@Param("serviceName") String serviceName);

    int saveServiceInfo(@Param("info") ServiceInfo info);

    int updateServiceInfo(@Param("info") ServiceInfo info);

    List<ServiceStatItemOutput> countByLoadType();
}
