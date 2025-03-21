package com.swjtu.service;

import com.swjtu.entity.DiscussPost;
import com.swjtu.entity.PageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DiscussPostService {
    PageBean page(Integer userId, Integer offst, Integer limit);

    int selectDiscussPostRows(@Param("userId") Integer userId);

    List<DiscussPost> findDiscussPosts(Integer userId, Integer offset, Integer limit);
}
