package com.kyriexu.service;

import com.kyriexu.model.ServicePageBean;

/**
 * @author KyrieXu
 * @since 2021/3/23 18:15
 **/
public interface ServiceService {
    ServicePageBean getPageBean(int page, int size, String info);
}
