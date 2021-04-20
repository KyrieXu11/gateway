package com.kyriexu.common.utils;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author KyrieXu
 * @since 2021/3/23 15:59
 **/
public class UtilsTest {

    public void testGetSaltyPass() {
        System.out.println(TimeUnit.SECONDS.toMillis(10));


        String pass = Utils.getSaltyPass("admin", "123456");
        System.out.println(pass);
        // assertEquals("729b32578a0865500e8a0ecf749718aea3c96676ca88fcec811d2537aa0c3aec",pass);
    }

    @Test
    public void encode() throws NoSuchAlgorithmException {
        // 449441eb5e72dca9c42a12f3924ea3a2
        // 202cb962ac59075b964b07152d234b70
        // 12012c1b91621ac15910715b19614b10711512d12314b170
        String md5 = Utils.encode("md5", UUID.randomUUID().toString().substring(1, 3));
        System.out.println(md5);
    }
}