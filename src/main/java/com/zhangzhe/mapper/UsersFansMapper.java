package com.zhangzhe.mapper;



import com.zhangzhe.pojo.UsersFans;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersFansMapper {

    void insertUserFans(UsersFans usersFans);

    void deleteUserFans(String userId,String fanId);

}