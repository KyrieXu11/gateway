package com.kyriexu.service;

import com.kyriexu.dto.TcpRuleInput;

/**
 * @author KyrieXu
 * @since 2021/4/12 16:23
 **/
public interface TcpRuleService {
    boolean add(TcpRuleInput input);
}
