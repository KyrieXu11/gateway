package com.kyriexu.service;

/**
 * @author KyrieXu
 * @since 2021/4/2 17:54
 **/
public interface SubmitService {
    void removeToken(String token);

    void saveToken(String token);

    boolean isTokenExist(String token);
}
