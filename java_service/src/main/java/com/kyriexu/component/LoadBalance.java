package com.kyriexu.component;

/**
 * @author KyrieXu
 * @since 2021/3/14 10:51
 **/
public interface LoadBalance {

    void add(String... param);

    String get(String key);

    String get();
}
