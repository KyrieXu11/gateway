package com.kyriexu.component.loadbalance;

import com.kyriexu.component.LoadBalance;
import junit.framework.TestCase;

/**
 * @author KyrieXu
 * @since 2021/3/14 21:04
 **/
public class WeightRobinLoadBalanceTest extends TestCase {

    public void testGet() {
        LoadBalance lb = new WeightRobinLoadBalance();
        lb.add("127.0.0.1:2003", "1"); //0
        lb.add("127.0.0.1:2004", "2"); //1
        lb.add("127.0.0.1:2005", "1"); //2
        lb.add("127.0.0.1:2006", "2"); //3
        lb.add("127.0.0.1:2007", "3"); //4

        String s = lb.get();
        System.out.println(s);
        s = lb.get();
        System.out.println(s);
        s = lb.get();
        System.out.println(s);
        s = lb.get();
        System.out.println(s);
        s = lb.get();
        System.out.println(s);
        s = lb.get();
        System.out.println(s);
    }
}