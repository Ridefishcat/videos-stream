package com.zhangzhe.service;

import com.zhangzhe.pojo.Comments;
import com.zhangzhe.pojo.Videos;
import com.zhangzhe.utils.PagedResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VideoService {

    String saveVideo(Videos video);

    PagedResult getAllVideos(Videos video,Integer isSaveRecord,Integer page,Integer pageSize);

    void userLikeVideo(String userId, String videoId, String videoCreateId);

    void userUnLikeVideo(String userId, String videoId, String videoCreateId);

    List<String> getHotWords();

    PagedResult queryMyLikeVideos(String userId,Integer page,Integer pageSize);

    PagedResult queryMyFollowVideos(String userId,Integer page,Integer pageSize);

    void saveComment(Comments comment);

    PagedResult getAllComments(String videoId, Integer page, Integer pageSize);
}
