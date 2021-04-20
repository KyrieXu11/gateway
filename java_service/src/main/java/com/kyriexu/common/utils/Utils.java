package com.kyriexu.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyriexu.exception.BaseException;
import com.kyriexu.exception.ResultCode;
import org.apache.commons.codec.binary.Hex;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * @author KyrieXu
 * @since 2021/3/23 14:52
 **/
public class Utils {
    /**
     * 获取加盐后的密码
     *
     * @param salt     数据库查询的盐
     * @param password 前端传递的密码
     * @return 加盐后的密码
     */
    public static String getSaltyPass(String salt, String password) {
        String str = getSHA256Str(password);
        return getSHA256Str(str + salt);
    }


    public static String getSHA256Str(String str) {
        MessageDigest messageDigest;
        String res = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes(StandardCharsets.UTF_8));
            res = Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static boolean isLogin(HttpSession session) {
        Object attribute = session.getAttribute(Constant.USER);
        return !Objects.isNull(attribute);
    }

    public static int getPage(int page, int size) {
        if (page <= 0 || size <= 0) {
            throw new BaseException(ResultCode.PAGE_NUM_ILLEGAL);
        }
        return (page - 1) * size;
    }

    public static boolean contains(String[] list, String s) {
        for (String str : list) {
            if (s.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static void output(OutputStream out, ObjectMapper mapper, Object o) throws IOException {
        try(BufferedOutputStream newOut = new BufferedOutputStream(out)){
            String s = mapper.writeValueAsString(o);
            newOut.write(s.getBytes());
            newOut.flush();
        }
    }

    public static String encode(String algorithm,String str) throws NoSuchAlgorithmException {
        MessageDigest encoder = MessageDigest.getInstance(algorithm);
        byte[] array = encoder.digest(str.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(Integer.toHexString((b & 0xFF) | 0x100).toLowerCase(),1,3);
        }
        return sb.toString();
    }
}
