package com.swjtu.service;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swjtu.entity.DiscussPost;
import com.swjtu.entity.PageBean;
import com.swjtu.mapper.DiscussPostMapper;
import com.swjtu.util.SensitiveFilter;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DiscussPostServiceImpl implements DiscussPostService{

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Value("${caffeine.posts.max-size}")
    private int maxSize;

    @Value("${caffeine.posts.expire-seconds}")
    private int expireSeconds;

    //帖子列表缓存
    private LoadingCache<String, List<DiscussPost>> postListCache;

    //帖子总数缓存
    private LoadingCache<Integer, Integer> postRowsCache;

    @PostConstruct
    public void init(){
        //初始化帖子列表缓存
            postListCache = Caffeine.newBuilder()
                    .maximumSize(maxSize)
                    .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
                    .build(new CacheLoader<String, List<DiscussPost>>() {
                        @Override
                        public List<DiscussPost> load(String key) throws Exception {
                            //没有数据时进行查询
                            if (key == null || key.length() == 0) throw new IllegalArgumentException("参数错误！");
                            String[] params = key.split(":");
                            if (params == null || params.length != 2) throw new IllegalArgumentException("参数错误！");
                            int offset = Integer.valueOf(params[0]);
                            int limit = Integer.valueOf(params[1]);

                            //二级缓存的位置

                            log.debug("load post list from DB.");
                            return discussPostMapper.selectDiscussPosts(0, offset, limit, 1);
                        }
                    });
        //初始化缓存帖子总数
        postRowsCache = Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
                .build(new CacheLoader<Integer, Integer>() {
                    @Override
                    public Integer load(Integer key) throws Exception {
                        log.debug("load post rows from DB.");
                        return discussPostMapper.selectDiscussPostRows(key);
                    }
                });
    }

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

        if (userId == 0) return postRowsCache.get(userId);
        log.debug("load post rows from DB.");
        return discussPostMapper.selectDiscussPostRows(userId);
    }

    @Override
    public List<DiscussPost> findDiscussPosts(Integer userId, Integer offset, Integer limit, Integer orderMode) {
        if (userId == 0 && orderMode == 1){
            return postListCache.get(offset + ":" + limit);
        }

        log.debug("load post list from DB.");
        return discussPostMapper.selectDiscussPosts(userId, offset, limit, orderMode);
    }

    @Override
    public int addDiscussPost(DiscussPost discussPost) {
        if (discussPost == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        //转义HTML标记
        discussPost.setTitle(HtmlUtils.htmlEscape(discussPost.getTitle()));
        discussPost.setContent(HtmlUtils.htmlEscape(discussPost.getContent()));
        //过滤敏感词
        discussPost.setTitle(sensitiveFilter.filter(discussPost.getTitle()));
        discussPost.setContent(sensitiveFilter.filter(discussPost.getContent()));
        return discussPostMapper.insertDiscussPost(discussPost);
    }

    @Override
    public DiscussPost findDiscussPostById(int id) {
        return discussPostMapper.selectDiscussPostById(id);
    }

    @Override
    public int updateCommentCount(int id, int commentCount) {
        return discussPostMapper.updateCommentCount(id, commentCount);
    }

    @Override
    public int updateType(int id, int type) {
        return discussPostMapper.updateType(id, type);
    }

    @Override
    public int updateStatus(int id, int status) {
        return discussPostMapper.updateStatus(id, status);
    }

    @Override
    public int updateScore(int id, double score) {
        return discussPostMapper.updateScore(id, score);
    }


}
