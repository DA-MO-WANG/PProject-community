package com.newcoder.community.service;

import com.newcoder.community.entity.User;
import com.newcoder.community.util.CommunityConstant;
import com.newcoder.community.util.RedisKeyUtil;
import io.lettuce.core.RedisURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FollowService  implements CommunityConstant {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;
    //关注业务
    public void follow(int userId, int entityType, int entityId) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
                String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
                redisOperations.multi();
                redisOperations.opsForZSet().add(followeeKey,entityId,System.currentTimeMillis());
                redisOperations.opsForZSet().add(followeeKey,userId,System.currentTimeMillis());

                return redisOperations.exec();
            }
        });
    }
    //取关业务
    public void unFollow(int userId, int entityType, int entityId) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
                String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
                redisOperations.multi();
                redisOperations.opsForZSet().remove(followeeKey,entityId);
                redisOperations.opsForZSet().remove(followeeKey,userId);

                return redisOperations.exec();
            }
        });
    }

    //关注的实体数量
    public long findFolloweeCount(int userId, int entityType) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return redisTemplate.opsForZSet().zCard(followeeKey);
    }
    //查询粉丝数量
    public long findFollowerCount(int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return redisTemplate.opsForZSet().zCard(followerKey);

    }
    //判断当前用户是否关注该实体
    public boolean hasFollowed(int userId, int entityType, int entityId) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return redisTemplate.opsForZSet().score(followeeKey,entityId) != null;
    }

    //查询用户关注的人
    public List<Map<String,Object>> findFollowee(int userId, int offset, int limit) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, ENTITY_TYPE_USER);
        Set<Integer> targetIds = redisTemplate.opsForZSet().reverseRange(followeeKey, offset, offset + limit - 1);

        if (targetIds == null) {
            return null;
        }
        List<Map<String,Object>> list = new ArrayList<>();

        for (Integer targetId : targetIds) {
            Map<String,Object> map = new HashMap<>();
            User user = userService.findUser(targetId);
            map.put("user",user);
            Double score = redisTemplate.opsForZSet().score(followeeKey, userId);
            map.put("followTime",new Date(score.longValue()));
            list.add(map);
        }
        return list;
    }

    //查询某用户的粉丝
    public List<Map<String,Object>> findFollowers(int userId,int offset, int limit) {
        String followerKey = RedisKeyUtil.getFollowerKey(ENTITY_TYPE_USER, userId);
        Set<Integer> targetIds = redisTemplate.opsForZSet().reverseRange(followerKey, offset, limit + offset - 1);

        if (targetIds == null) {
            return null;
        }

        List<Map<String,Object>> list = new ArrayList<>();
        for (Integer targetId : targetIds) {
            Map<String,Object> map = new HashMap<>();
            User user = userService.findUser(targetId);
            map.put("user",user);
            Double score = redisTemplate.opsForZSet().score(followerKey, userId);
            map.put("followTime",new Date(score.longValue()));
            list.add(map);
        }
        return list;

    }






}
