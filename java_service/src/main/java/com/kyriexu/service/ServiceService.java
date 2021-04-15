package com.kyriexu.service;

import com.kyriexu.dto.DashServiceStatOutput;
import com.kyriexu.dto.SearchInput;
import com.kyriexu.dto.ServiceListItem;
import com.kyriexu.dto.ServiceSearch;
import com.kyriexu.model.PageBean;
import com.kyriexu.model.ServiceDetail;
import com.kyriexu.model.ServiceInfo;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/3/23 18:15
 **/
public interface ServiceService {
    PageBean<ServiceListItem> getPageBean(SearchInput searchInput);

    ServiceDetail getServiceDetail(ServiceSearch search);

    ServiceDetail getServiceDetail(ServiceInfo info);

    int getTotalPage(SearchInput input);

    /**
     * 添加 ServiceInfo
     *
     * @param serviceInfo serviceInfo
     * @return 新增的实体的主键
     */
    int saveServiceInfo(ServiceInfo serviceInfo);

    List<ServiceInfo> getServiceInfoList(SearchInput searchInput);

    DashServiceStatOutput countByLoadType();

    boolean del(Long serviceId);

    List<ServiceDetail> getServiceDetails(SearchInput input);
}
