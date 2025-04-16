package com.swjtu.mapper;

import com.swjtu.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    //分页查询，用pagehelper实现
     List<DiscussPost> page(Integer userId, Integer offset, Integer limit) ;

     int selectDiscussPostRows(Integer userId);

    List<DiscussPost> selectDiscussPosts(Integer userId, Integer offset, Integer limit);

    //发布新帖
    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(int id);

    int updateCommentCount(int id, int commentCount);
}
