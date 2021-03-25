package com.kyriexu.service;

import com.kyriexu.dto.ServiceInput;
import com.kyriexu.dto.ServiceSearch;
import com.kyriexu.model.ServiceDetail;
import com.kyriexu.model.ServiceInfo;
import com.kyriexu.model.ServicePageBean;

/**
 * @author KyrieXu
 * @since 2021/3/23 18:15
 **/
public interface ServiceService {
    ServicePageBean getPageBean(ServiceInput serviceInput);

    ServiceDetail getServiceDetail(ServiceSearch search);

    int getTotalPage(ServiceInput input);

    /**
     * 添加 SserviceInfo
     *
     * @param serviceInfo serviceInfo
     * @return 新增的实体的主键
     */
    long saveServiceInfo(ServiceInfo serviceInfo);
}
