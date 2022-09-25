package com.venson.security.entity.constant;

public  class AuthConstants {
    public static final String TokEN="X-Token";
    public static final String ADMIN_PREFIX="ADMIN";
    public static final String USER_PREFIX="USER";
    public static final Long EXPIRE_24H_MS= (long) (24*60*60*1000);
    public static final Long EXPIRE_2DAY_MS= (long) (48*60*60*1000);
    public static final Long EXPIRE_24H_S= (long) (24*60*60);
    public static final Long EXPIRE_2DAY_S= (long) (48*60*60);
    public static final String JWT_KEY="changliulab@20220000";
    public static final String LOGIN_PATH_ADMIN="/auth/admin/login";
    public static final String LOGIN_PATH_USER="/auth/front/login";
    public static final String FRONT_PATTERN="/*/front/**";
}
