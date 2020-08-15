package com.zhangzhe.service;

import com.zhangzhe.pojo.Users;
import com.zhangzhe.pojo.UsersReport;
import org.springframework.stereotype.Service;

@Service
public interface UsersService {

    boolean selectUserByUsername(Users user);

    boolean addUser(Users users);

    Users login(Users user) throws Exception;

    Integer updateUserInfo(Users user);

    Users queryUserInfo(String id);

    boolean isUserLikeVideo(String userId, String videoId);

    void saveUserFanRelation(String userId, String fanId);

    void deleteUserFanRelation(String userId, String fanId);

    void reportUser(UsersReport usersReport);
}
