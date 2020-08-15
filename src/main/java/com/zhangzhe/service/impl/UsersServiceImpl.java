package com.zhangzhe.service.impl;

import com.zhangzhe.mapper.UsersFansMapper;
import com.zhangzhe.mapper.UsersLikeVideosMapper;
import com.zhangzhe.mapper.UsersMapper;
import com.zhangzhe.mapper.UsersReportMapper;
import com.zhangzhe.pojo.Users;
import com.zhangzhe.pojo.UsersFans;
import com.zhangzhe.pojo.UsersLikeVideos;
import com.zhangzhe.pojo.UsersReport;
import com.zhangzhe.service.UsersService;
import com.zhangzhe.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;

    @Autowired
    private UsersFansMapper usersFansMapper;

    @Autowired
    private UsersReportMapper usersReportMapper;

    @Autowired
    private Sid sid;

    @Override
    public boolean selectUserByUsername(Users user) {

        Users users = usersMapper.selectUserByUsername(user.getUsername());
        return users == null;
    }

    @Override
    public boolean addUser(Users users) {
        users.setId(sid.nextShort());
        users.setNickname(users.getUsername());
        users.setFansCounts(0);
        users.setFollowCounts(0);
        users.setReceiveLikeCounts(0);
        Long aLong = usersMapper.insertUsers(users);
        return aLong >0;
    }

    @Override
    public Users login(Users user) throws Exception {
        String md5Str = MD5Utils.getMD5Str(user.getPassword());
        Users loginUser = usersMapper.selectUserByUsername(user.getUsername());
        if (md5Str.equals(loginUser.getPassword())){
            return loginUser;
        }else {
            return null;
        }
    }

    @Override
    public Integer updateUserInfo(Users user) {
        return usersMapper.updateUserFaceImage(user);
    }

    @Override
    public Users queryUserInfo(String id) {
        return usersMapper.queryUserInfoById(id);
    }

    @Override
    public boolean isUserLikeVideo(String userId, String videoId) {
        UsersLikeVideos usersLikeVideos = usersLikeVideosMapper.selectUsersLikeVideos(userId, videoId);
        return usersLikeVideos != null;
    }

    @Transactional
    @Override
    public void saveUserFanRelation(String userId, String fanId) {
        UsersFans build = UsersFans.builder()
                .id(sid.nextShort())
                .userId(userId)
                .fanId(fanId)
                .build();
        usersFansMapper.insertUserFans(build);
        usersMapper.addFansCount(userId);
        usersMapper.addFollersCount(fanId);
    }

    @Transactional
    @Override
    public void deleteUserFanRelation(String userId, String fanId) {
        usersFansMapper.deleteUserFans(userId,fanId);
        usersMapper.reduceFansCount(userId);
        usersMapper.reduceFollersCount(fanId);
    }

    @Override
    public void reportUser(UsersReport userReport) {
        String urId = sid.nextShort();
        userReport.setId(urId);
        usersReportMapper.insert(userReport);
    }

}
