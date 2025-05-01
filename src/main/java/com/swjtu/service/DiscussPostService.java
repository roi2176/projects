package com.swjtu.service;

import com.swjtu.entity.DiscussPost;
import com.swjtu.entity.PageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DiscussPostService {
    PageBean page(Integer userId, Integer offst, Integer limit);

    int selectDiscussPostRows(@Param("userId") Integer userId);

    List<DiscussPost> findDiscussPosts(Integer userId, Integer offset, Integer limit, Integer orderMode);

    int addDiscussPost(DiscussPost discussPost);

    DiscussPost findDiscussPostById(int id);

    int updateCommentCount(int id, int commentCount);

    int updateType(int id, int type);

    int updateStatus(int id, int status);
    int updateScore(int id, double score);
}
