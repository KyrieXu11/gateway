package com.kyriexu.utils;

import com.kyriexu.exception.BaseException;
import com.kyriexu.exception.ResultCode;
import org.apache.commons.codec.binary.Hex;

import javax.servlet.http.HttpSession;
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
}
