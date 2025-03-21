package com.swjtu.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swjtu.entity.DiscussPost;
import com.swjtu.entity.PageBean;
import com.swjtu.mapper.DiscussPostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostServiceImpl implements DiscussPostService{

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Override
    public PageBean page(Integer userId, Integer offst, Integer limit) {
        //设置分页参数
        PageHelper.startPage(offst, limit);
        //执行分页查询
        List<DiscussPost> discussPostsList = discussPostMapper.page(userId, offst, limit);
        //获取分页结果
        Page<DiscussPost> p = (Page<DiscussPost>) discussPostsList;
        //封装PageBean
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    @Override
    public int selectDiscussPostRows(Integer userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }

    @Override
    public List<DiscussPost> findDiscussPosts(Integer userId, Integer offset, Integer limit) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }


}
