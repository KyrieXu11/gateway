package com.kyriexu.component.loadbalance;

/**
 * @author KyrieXu
 * @since 2021/3/14 15:00
 **/
public class WeightNode {
    /**
     * 节点的本来的权重
     */
    private Integer weight;
    /**
     * 节点的地址 ip:port
     */
    private String host;
    /**
     * 节点的当前的权重，因为当前的权重在不同的情况下会发生变化
     */
    private int currentWeight;
    /**
     * 有效权重默认与权重相同，通讯异常时-1, 通讯成功+1，直到恢复到weight大小
     */
    private Integer effectiveWeight;

    public WeightNode(String host, Integer weight) {
        this.host = host;
        this.weight = weight;
        this.effectiveWeight = weight;
    }

    public Integer getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Integer currentWeight) {
        this.currentWeight = currentWeight;
    }

    public Integer getEffectiveWeight() {
        return effectiveWeight;
    }

    public void setEffectiveWeight(Integer effectiveWeight) {
        this.effectiveWeight = effectiveWeight;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
