package com.zhangzhe.service.impl;

import com.zhangzhe.mapper.BgmMapper;
import com.zhangzhe.pojo.Bgm;
import com.zhangzhe.service.BgmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BgmServiceImpl implements BgmService {

    @Autowired
    BgmMapper bgmMapper;

    @Override
    public void addBgm(Bgm bgm) {
        bgmMapper.insertBgm(bgm);
    }

    @Override
    public Bgm queryBgmById(String id) {
        return bgmMapper.queryBgmById(id);
    }

    @Override
    public List<Bgm> queryBgmList() {
        return bgmMapper.queryList();
    }


}
