package com.swjtu.controller;

import com.swjtu.annotation.LoginRequired;
import com.swjtu.entity.User;
import com.swjtu.service.FollowService;
import com.swjtu.service.LikeService;
import com.swjtu.service.UserService;
import com.swjtu.util.AliOSSUtils;
import com.swjtu.util.CommunityConstant;
import com.swjtu.util.CommunityUtil;
import com.swjtu.util.HostHolder;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController implements CommunityConstant {

    //域名
    @Value("${community.path.domain}")
    private String domain;

    //上下文
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${community.path.upload}")
    private String uploadPath;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private FollowService followService;

    @Autowired
    private AliOSSUtils aliOSSUtils;

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.headerBucket}")
    private String headerBucketName;


    @GetMapping("/setting")
    public String getSettingPage() {
        return "/site/setting";
    }

    //更新头像路径
//    @PostMapping("/header/url")
//    @ResponseBody
//    public String updateHeaderUrl(String fileName){
//        if (StringUtils.isBlank(fileName)) {
//            return CommunityUtil.getJSONString(1, "文件名不能为空!");
//        }
//
//        String url = endpoint.split("//")[0] + "//" + headerBucketName + "." + endpoint.split("//")[1] + "/" + fileName;
//        userService.updateHeader(hostHolder.getUser().getId(), url);
//
//        return CommunityUtil.getJSONString(0);
//    }

    //废弃
//    @LoginRequired
//    @PostMapping("/upload")
//    public String uploadHeader(MultipartFile headerImage, Model model) {
//        if (headerImage == null) {
//            model.addAttribute("error", "您还没有选择图片");
//            return "/site/setting";
//        }
//
//        String filename = headerImage.getOriginalFilename();
//        String suffix = filename.substring(filename.lastIndexOf("."));
//        if (StringUtils.isBlank(suffix)) {
//            model.addAttribute("error", "文件格式不正确");
//            return "/site/setting";
//        }
//
//        //获取本地储存路径并存放
//        filename = CommunityUtil.generateUUID() + suffix;
//        File dest = new File(uploadPath + "/" + filename);
//        try {
//            headerImage.transferTo(dest);
//        } catch (IOException e) {
//            log.error("上传文件失败：" + e.getMessage());
//            throw new RuntimeException("上传文件失败，服务器发生异常", e);
//        }
//
//        //更新web访问路径
//        // http://localhost:8080/community/user/header/xxx.png
//        User user = hostHolder.getUser();
//        String headerUrl = domain + contextPath + "/user/header/" + filename;
//        userService.updateHeader(user.getId(), headerUrl);
//
//        return "redirect:/index";
//    }

    @LoginRequired
    @PostMapping("/upload")
    public String uploadHeader(MultipartFile headerImage, Model model) throws IOException{
        if (headerImage == null) {
            model.addAttribute("error", "您还没有选择图片");
            return "/site/setting";
        }

        String uuid = UUID.randomUUID().toString();

        String url = aliOSSUtils.upload(headerImage, uuid);

        //更新web访问路径
        User user = hostHolder.getUser();
        userService.updateHeader(user.getId(), url);
        return "redirect:/index";
    }

    //废弃
    @GetMapping("/header/{filename}")
    public void getHeader(@PathVariable("filename") String fileName, HttpServletResponse response) {
        fileName = uploadPath + "/" + fileName;
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        response.setContentType("image/" + suffix);
        try (
                ServletOutputStream os = response.getOutputStream();
                FileInputStream fis = new FileInputStream(fileName);
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1){
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            log.error("读取头像失败：" + e.getMessage());
        }
    }

    //个人主页
    @GetMapping("/profile/{userId}")
    public String getProfilePage(@PathVariable("userId") int userId, Model model){
        User user = userService.findUserById(userId);
        //避免恶意攻击
        if (user == null) {
            throw new RuntimeException("该用户不存在");
        }
        //用户
        model.addAttribute("user", user);
        int likeCount = likeService.findUserLikeCount(userId);
        model.addAttribute("likeCount", likeCount);

        //关注数量
        long followeeCount = followService.findFolloweeCount(userId, ENTITY_TYPE_USER);
        model.addAttribute("followeeCount", followeeCount);
        //粉丝数量
        long followerCount = followService.findFollowerCount(ENTITY_TYPE_USER, userId);
        model.addAttribute("followerCount", followerCount);
        //是否已关注
        //如果未登录默认false，然后判断是否登录
        boolean hasFollowed = false;
        if (hostHolder.getUser() != null) {
            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, userId);
        }
        model.addAttribute("hasFollowed", hasFollowed);
        return "/site/profile";
    }
}
