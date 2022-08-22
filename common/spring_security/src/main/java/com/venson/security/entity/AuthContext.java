package com.venson.security.entity;

import com.venson.security.entity.bo.UserContextInfoBO;

public class AuthContext {
    /** The request holder. */
    private static final ThreadLocal<UserContextInfoBO> USER_INFO = new ThreadLocal<>();

    public static UserContextInfoBO get() {
        return USER_INFO.get();
    }

    public static void set(UserContextInfoBO userInfoBo) {
        USER_INFO.set(userInfoBo);
    }

    public static void clean() {
        if (USER_INFO.get() != null) {
            USER_INFO.remove();
        }
    }
}
