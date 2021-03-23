package com.kyriexu.service.impl;

import com.kyriexu.model.ServicePageBean;
import com.kyriexu.service.ServiceService;
import org.springframework.stereotype.Service;

/**
 * @author KyrieXu
 * @since 2021/3/23 18:15
 **/
@Service
public class ServiceServiceImpl implements ServiceService {
    @Override
    public ServicePageBean getPageBean(int page, int size, String info) {
        return null;
    }
}
