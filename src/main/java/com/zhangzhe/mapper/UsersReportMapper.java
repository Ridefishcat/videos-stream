package com.zhangzhe.mapper;

import com.zhangzhe.pojo.UsersReport;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersReportMapper {
    void insert(UsersReport usersReport);
}
