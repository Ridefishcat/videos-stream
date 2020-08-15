package com.zhangzhe.mapper;


import com.zhangzhe.pojo.Bgm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BgmMapper {

    Long insertBgm(Bgm bgm);

    List<Bgm> queryList();

    Bgm queryBgmById(String id);
}