package com.kyriexu.model;

import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/3/23 18:17
 **/
@NoArgsConstructor
public class PageBean<T> {
    private int total;
    private int current;
    private List<T> items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public PageBean(int total, int current, List<T> items) {
        this.total = total;
        this.current = current;
        this.items = items;
    }
}
