package com.kyriexu.service;

import com.kyriexu.dto.HttpRuleInput;

/**
 * @author KyrieXu
 * @since 2021/3/24 20:19
 **/
public interface HttpRuleService {

    boolean add(HttpRuleInput httpRuleInput);

    boolean update(HttpRuleInput httpRuleInput);
}
