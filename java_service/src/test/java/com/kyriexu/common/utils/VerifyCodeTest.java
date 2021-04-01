package com.kyriexu.common.utils;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author KyrieXu
 * @since 2021/4/1 16:42
 **/
public class VerifyCodeTest {

    @Test
    public void getBase64() throws IOException {
        VerifyCode code = new VerifyCode();
        File file = new File("./a.png");
        FileOutputStream outputStream = new FileOutputStream(file);
        VerifyCode.output(code,outputStream);
        String base64 = code.getBase64();
        System.out.println(base64);
    }
}