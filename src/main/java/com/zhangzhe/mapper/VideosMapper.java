package com.zhangzhe.mapper;

import com.zhangzhe.pojo.Videos;
import com.zhangzhe.pojo.response.vo.VideosVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideosMapper {

    Long insertVideo(Videos video);

    List<VideosVo> queryAllVideos(@Param("videoDesc") String videoDesc,
                                  @Param("userId") String userId);

    void addVideoLikeCount(String videoId);

    void reduceVideoLikeCount(String videoId);

    List<VideosVo> queryMyLikeVideos(@Param("userId") String userId);

    List<VideosVo> queryMyFollowVideos(@Param("userId") String userId);
}