package com.newcoder.community.util;

public class RedisKeyUtil {
    private static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    private static final String PREFIX_USER_LIKE = "like:user";
    private static final String PREFIX_FOLLOWER = "followee";
    private static final String PREFIX_FOLLWER = "follower";
    private static final String PREFIX_UV = "uv";
    private static final String PREFIX_DAU = "dau";
    private static final String PREFIX_POST = "post";

    //制造点赞的key
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT +entityId;
    }
    //针对用户的点赞
    public static String getUserLikeKey(int userId) {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }
    //针对实体的目标
    public static String getFolloweeKey(int userId, int entityType) {
        return PREFIX_FOLLOWER + SPLIT + userId + SPLIT +entityType;
    }
    //针对实体的粉丝
    public static String getFollowerKey(int entityType , int entityId) {
        return PREFIX_FOLLWER + SPLIT + entityType + SPLIT + entityId;
    }
    //单日UV
    public static String getUVKey(String date) {
        return PREFIX_UV + SPLIT + date;
    }
    //区间UV
    public static String getUVKey(String startDate, String endDate) {
        return PREFIX_UV + SPLIT + startDate + SPLIT + endDate;
    }
    //单日活跃用户
    public static String getDAUKey(String date) {
        return PREFIX_DAU + SPLIT + date;
    }
    //区间活跃用户
    public static String getDAUKey(String startDate, String endDate) {
        return PREFIX_DAU +SPLIT + startDate + SPLIT + endDate;
    }
    //影响score的帖子key
    public static String getPostScoreKey() {
        return PREFIX_POST + SPLIT +"score";
    }

}
