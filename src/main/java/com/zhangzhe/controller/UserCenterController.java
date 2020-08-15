package com.zhangzhe.controller;

import com.zhangzhe.pojo.Users;
import com.zhangzhe.pojo.UsersReport;
import com.zhangzhe.pojo.response.vo.PublisherVideo;
import com.zhangzhe.pojo.response.vo.UsersVo;
import com.zhangzhe.service.UsersService;
import com.zhangzhe.utils.ApiResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("user")
public class UserCenterController {

    @Autowired
    UsersService usersService;

    @PostMapping("/uploadFace")
    public ApiResult upload(@RequestParam("file") MultipartFile[] files, HttpServletRequest httpServletRequest) throws IOException {
        String id = httpServletRequest.getHeader("headerUserId");
        String fileSpace = "C:/imooc_videos_dev";
        String uploadPathDB = "/" + id + "/face";
        InputStream inputStream = null;
        if (files != null && files.length > 0) {
            String fileName = files[0].getOriginalFilename();
            if (StringUtils.isNotBlank(fileName)) {
                // 文件上传的最终保存路径
                String finalFacePath = fileSpace + uploadPathDB + "/" + fileName;
                // 设置数据库保存的路径
                uploadPathDB += ("/" + fileName);

                File outFile = new File(finalFacePath);
                if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                    // 创建父文件夹
                    outFile.getParentFile().mkdirs();
                }
                inputStream = files[0].getInputStream();
                IOUtils.copy(inputStream, new FileOutputStream(outFile));
            }

        } else {
            return ApiResult.errorMsg("上传出错...");
        }


        Users user = new Users();
        user.setId(id);
        user.setFaceImage(uploadPathDB);
        usersService.updateUserInfo(user);
        return ApiResult.ok(uploadPathDB);
    }

    @GetMapping("/query")
    public ApiResult queryUserInfo(String id) {
        Users users = usersService.queryUserInfo(id);
        return ApiResult.ok(users);
    }

    @PostMapping("/queryPublisher")
    public ApiResult queryPublisher(String loginUserId, String videoId,
                                    String publishUserId) {
        if (StringUtils.isBlank(publishUserId)) {
            return ApiResult.errorMsg("");
        }
        Users users = usersService.queryUserInfo(publishUserId);
        UsersVo publisher = new UsersVo();
        BeanUtils.copyProperties(users, publisher);

        boolean userLikeVideo = usersService.isUserLikeVideo(loginUserId, videoId);

        PublisherVideo build = PublisherVideo.builder().publisher(publisher)
                .userLikeVideo(userLikeVideo)
                .build();
        return ApiResult.ok(build);


    }

    @PostMapping("/beyourfans")
    public ApiResult beyourfans(String userId, String fanId)  {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)) {
            return ApiResult.errorMsg("");
        }
        usersService.saveUserFanRelation(userId, fanId);
        return ApiResult.ok("关注成功...");
    }

    @PostMapping("/dontbeyourfans")
    public ApiResult dontbeyourfans(String userId, String fanId)   {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)) {
            return ApiResult.errorMsg("");
        }
        usersService.deleteUserFanRelation(userId, fanId);
        return ApiResult.ok("取消关注成功...");
    }

    @PostMapping("/reportUser")
    public ApiResult reportUser(@RequestBody UsersReport usersReport) throws Exception {
        // 保存举报信息
        usersService.reportUser(usersReport);
        return ApiResult.errorMsg("举报成功...有你平台变得更美好...");
    }

}
