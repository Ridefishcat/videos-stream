package com.zhangzhe.mapper;



import com.zhangzhe.pojo.UsersLikeVideos;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UsersLikeVideosMapper {

    UsersLikeVideos selectUsersLikeVideos(@Param("userId") String userId,@Param("videoId") String videoId);

    void insert(UsersLikeVideos usersLikeVideos);

    void delete(String videoId);
}