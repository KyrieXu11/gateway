package com.kyriexu.component.loadbalance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KyrieXu
 * @since 2021/3/14 20:28
 **/
public class WeightRobinLoadBalance extends AbstractLoadBalance {
    private final List<WeightNode> hosts;

    public WeightRobinLoadBalance() {
        hosts = new ArrayList<>();
    }

    @Override
    public String get() {
        // 所有节点的有效权重之和
        Integer total = 0;
        WeightNode best = null;
        for (WeightNode node : hosts) {
            total += node.getEffectiveWeight();
            node.setCurrentWeight(node.getCurrentWeight() + node.getEffectiveWeight());
            if (node.getEffectiveWeight() < node.getWeight()) {
                node.setEffectiveWeight(node.getEffectiveWeight() + 1);
            }
            if (best == null || node.getCurrentWeight() > best.getCurrentWeight()) {
                best = node;
            }
        }
        if (best == null)
            return "";
        best.setCurrentWeight(best.getCurrentWeight() - total);
        return best.getHost();
    }

    @Override
    public void addHosts(String... node) {
        WeightNode n = new WeightNode(node[0], Integer.parseInt(node[1]));
        this.hosts.add(n);
    }
}
