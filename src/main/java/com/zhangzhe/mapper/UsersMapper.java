package com.zhangzhe.mapper;

import com.zhangzhe.pojo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UsersMapper {

    Users selectUserByUsername(@Param("userName")String userName);

    Long insertUsers(Users users);

    Integer updateUserFaceImage(Users users);

    Users queryUserInfoById(@Param("id")String id);

    /**
     * 增加我接受到的赞美/收藏 的数量
     * @param id
     */
    void addReceiveLikeCount(String id);
    /**
     * 减少我接受到的赞美/收藏 的数量
     * @param id
     */
    void reduceReceiveLikeCount(String id);

    /**
     * @Description: 增加粉丝数
     */
    void addFansCount(String userId);

    /**
     * @Description: 增加关注数
     */
    void addFollersCount(String userId);

    /**
     * @Description: 减少粉丝数
     */
    void reduceFansCount(String userId);

    /**
     * @Description: 减少关注数
     */
    void reduceFollersCount(String userId);
}
