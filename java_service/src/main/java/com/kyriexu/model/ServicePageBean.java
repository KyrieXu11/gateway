package com.kyriexu.model;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/3/23 18:17
 **/
public class ServicePageBean {
    private int total;
    private int current;
    private List<ServiceListItem> items;

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

    public List<ServiceListItem> getItems() {
        return items;
    }

    public void setItems(List<ServiceListItem> items) {
        this.items = items;
    }

    public ServicePageBean(int total, int current, List<ServiceListItem> items) {
        this.total = total;
        this.current = current;
        this.items = items;
    }
}
