package com.newcoder.community.dao;

import com.newcoder.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    List<Message> selectConversations(int userId, int offset, int limit);
    int selectConversationCount(int userId);
    List<Message> selectLetters(String conversationId,int offset,int limit);
    int selectLetterCount(String conversationId);
    int selectLetterUnreadCount(int userId ,String conversationId);
    //新增一条消息
    int insertMessage(Message message);
    //修改消息状态
    int updateStatus(List<Integer> ids,int status);
    //查询某个主题下最新的通知
    Message selectLatestNotice(int userId,String topic);
    //查询某个主题包含的通知数量
    int selectNoticeCount(int userId,String topic);
    //查询未读通知数量
    int selectNoticeUnreadCount(int userId, String topic);

    List<Message> selectNotices(int userId, String topic,int offset,int limit);
}
