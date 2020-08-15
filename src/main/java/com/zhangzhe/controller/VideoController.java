package com.zhangzhe.controller;

import com.zhangzhe.pojo.Bgm;
import com.zhangzhe.pojo.Comments;
import com.zhangzhe.pojo.Users;
import com.zhangzhe.pojo.Videos;
import com.zhangzhe.service.BgmService;
import com.zhangzhe.service.UsersService;
import com.zhangzhe.service.VideoService;
import com.zhangzhe.utils.ApiResult;
import com.zhangzhe.utils.FetchVideoCover;
import com.zhangzhe.utils.MergeVideoMp3;
import com.zhangzhe.utils.PagedResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("video")
public class VideoController extends BasicController {

    @Autowired
    UsersService usersService;

    @Autowired
    BgmService bgmService;

    @Autowired
    VideoService videoService;

    @PostMapping("/upload")
    public ApiResult upload(@RequestParam("file") MultipartFile files, HttpServletRequest httpServletRequest) throws Exception {
        String id = httpServletRequest.getHeader("headerUserId");
        String bgmId = httpServletRequest.getHeader("bgmId");
        double videoSeconds = Double.parseDouble(httpServletRequest.getHeader("videoSeconds"));
        String videoWidth = httpServletRequest.getHeader("videoWidth");
        String videoHeight = httpServletRequest.getHeader("videoHeight");
        String desc = httpServletRequest.getHeader("desc");
        String uploadPathDB = "\\" + id + "\\video";
        String coverPathDB = uploadPathDB;
        InputStream inputStream = null;
        String finalVideoPath = null;
        if (files != null) {
            String fileName = files.getOriginalFilename();
            coverPathDB = coverPathDB + "\\" + UUID.randomUUID() + ".jpg";
            if (StringUtils.isNotBlank(fileName)) {
                // 文件上传的最终保存路径
                finalVideoPath = FILE_SPACE + uploadPathDB + "\\" + fileName;

                // 设置数据库保存的路径
                uploadPathDB += ("\\" + fileName);
                File outFile = new File(finalVideoPath);
                if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                    // 创建父文件夹
                    outFile.getParentFile().mkdirs();
                }
                inputStream = files.getInputStream();
                IOUtils.copy(inputStream, new FileOutputStream(outFile));
            }
        } else {
            return ApiResult.errorMsg("上传出错...");
        }
        if (StringUtils.isNotBlank(bgmId)) {
            Bgm bgm = bgmService.queryBgmById(bgmId);
            String mp3InputPath = FILE_SPACE + bgm.getPath();
            MergeVideoMp3 tool = new MergeVideoMp3(FFMPEG_EXE);
            String videoInputPath = finalVideoPath;
            String videoOutputName = UUID.randomUUID().toString() + ".mp4";
            uploadPathDB = "\\" + id + "\\video" + "\\" + videoOutputName;
            finalVideoPath = FILE_SPACE + uploadPathDB;
            tool.convertor(videoInputPath, mp3InputPath, videoSeconds, finalVideoPath);
            // 对视频进行截图
            Thread.sleep(2000);
            FetchVideoCover videoInfo = new FetchVideoCover(FFMPEG_EXE);
            String outPutPhoto = FILE_SPACE + coverPathDB;
            videoInfo.getCover(videoInputPath, outPutPhoto);
        }
        String s = coverPathDB.replaceAll("\\\\", "/");
        Videos build = Videos.builder().audioId(bgmId)
                .userId(id)
                .videoSeconds((float) videoSeconds)
                .videoHeight(Integer.valueOf(videoHeight))
                .videoWidth(Integer.valueOf(videoWidth))
                .videoDesc(desc)
                .videoPath(uploadPathDB)
                .coverPath(s)
                .likeCounts(0L)
                .status(1).build();
        String videoId = videoService.saveVideo(build);
        return ApiResult.ok(videoId);
    }

    @GetMapping("/query")
    public ApiResult queryUserInfo(String id) {
        try {
            Users users = usersService.queryUserInfo(id);
            return ApiResult.ok(users);
        } catch (Exception e) {
            return ApiResult.errorMsg(e.getMessage());
        }
    }

    @PostMapping("/showAll")
    public ApiResult showAll(@RequestBody Videos video, Integer isSaveRecord,Integer page, Integer pageSize) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedResult result = videoService.getAllVideos(video,isSaveRecord,page, pageSize);
        return ApiResult.ok(result);
    }

    @PostMapping(value = "/hot")
    public ApiResult hot() {
        try {
            List<String> hotWords = videoService.getHotWords();
            return ApiResult.ok(hotWords);
        } catch (Exception e) {
            return ApiResult.errorMsg(e.getMessage());
        }
    }


    @PostMapping(value = "/userLike")
    public ApiResult userLike(String userId, String videoId, String videoCreaterId) {
        try {
            videoService.userLikeVideo(userId, videoId, videoCreaterId);
            return ApiResult.ok();
        } catch (Exception e) {
            return ApiResult.errorMsg(e.getMessage());
        }
    }

    @PostMapping(value = "/userUnLike")
    public ApiResult userUnLike(String userId, String videoId, String videoCreaterId) {
        try {
            videoService.userUnLikeVideo(userId, videoId, videoCreaterId);
            return ApiResult.ok();
        } catch (Exception e) {
            return ApiResult.errorMsg(e.getMessage());
        }
    }


    @PostMapping("/showMyLike")
    public ApiResult showMyLike(String userId, Integer page, Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return ApiResult.ok();
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 6;
        }
        PagedResult videosList = videoService.queryMyLikeVideos(userId, page, pageSize);
        return ApiResult.ok(videosList);
    }

    @PostMapping("/showMyFollow")
    public ApiResult showMyFollow(String userId, Integer page) throws Exception {
        if (StringUtils.isBlank(userId)) {
            return ApiResult.ok();
        }
        if (page == null) {
            page = 1;
        }
        int pageSize = 6;
        PagedResult videosList = videoService.queryMyFollowVideos(userId, page, pageSize);
        return ApiResult.ok(videosList);
    }

    /**
     * 保存评论
     * @param comment
     * @param fatherCommentId
     * @param toUserId
     * @return
     * @throws Exception
     */
    @PostMapping("/saveComment")
    public ApiResult saveComment(@RequestBody Comments comment,
                                       String fatherCommentId, String toUserId) {
        comment.setFatherCommentId(fatherCommentId);
        comment.setToUserId(toUserId);
        videoService.saveComment(comment);
        return ApiResult.ok();
    }

    @PostMapping("/getVideoComments")
    public ApiResult getVideoComments(String videoId, Integer page, Integer pageSize) {

        if (StringUtils.isBlank(videoId)) {
            return ApiResult.ok();
        }

        // 分页查询视频列表，时间顺序倒序排序
        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        PagedResult list = videoService.getAllComments(videoId, page, pageSize);

        return ApiResult.ok(list);
    }





}
