package com.kyriexu.component;

/**
 * @author KyrieXu
 * @since 2021/3/17 19:32
 **/
public interface Observed {
    void notice();

    void attach(Observer observer);
}
