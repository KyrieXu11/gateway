package com.kyriexu.component.loadbalance;

import com.kyriexu.component.LoadBalance;
import org.junit.Test;

/**
 * @author KyrieXu
 * @since 2021/3/14 11:08
 **/
public class RandomLoadBalanceTest {

    @Test
    public void add() {
        LoadBalance lb = new RandomLoadBalance();
        lb.add("127.0.0.1:2003"); //0
        lb.add("127.0.0.1:2004"); //1
        lb.add("127.0.0.1:2005"); //2
        lb.add("127.0.0.1:2006"); //3
        lb.add("127.0.0.1:2007"); //4

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

    }

    @Test
    public void get() {
    }
}