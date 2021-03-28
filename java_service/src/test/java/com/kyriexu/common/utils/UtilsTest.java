package com.kyriexu.common.utils;

import junit.framework.TestCase;

import java.util.concurrent.TimeUnit;

/**
 * @author KyrieXu
 * @since 2021/3/23 15:59
 **/
public class UtilsTest extends TestCase {

    public void testGetSaltyPass() {
        System.out.println(TimeUnit.SECONDS.toMillis(10));


        String pass = Utils.getSaltyPass("admin", "123456");
        System.out.println(pass);
        // assertEquals("729b32578a0865500e8a0ecf749718aea3c96676ca88fcec811d2537aa0c3aec",pass);
    }
}