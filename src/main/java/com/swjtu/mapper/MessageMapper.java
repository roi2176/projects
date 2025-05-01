package com.swjtu.mapper;

import com.swjtu.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {

    //查询某个用户的会话
    List<Message> selectConversations(int userId, int offset, int limit);

    //查询当前用户的会话数量
    int selectConversationCount(int userId);

    //查询某个会话包含的私信列表
    List<Message> selectLetters(String conversationId, int offset, int limit);

    //查询某个会话包含的私信数量
    int selectLetterCount(String conversationId);

    //查询未读私信数量
    int selectLetterUnreadCount(int userId, String conversationId);

    int insertMessage(Message message);

    int updateStatus(List<Integer> ids, int status);

    //查询某个主题下最新的通知
    Message selectLatestNotice(int userId, String topic);

    //查询某个主题下通知的数量
    int selectNoticeCount(int userId, String topic);

    //查询未读的通知的数量
    int selectNoticeUnreadCount(int userId,String topic);

    List<Message> selectNotices(int userId, String topic, int offset, int limit);
}
