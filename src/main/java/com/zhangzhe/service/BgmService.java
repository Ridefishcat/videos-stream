package com.zhangzhe.service;

import com.zhangzhe.pojo.Bgm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BgmService {
    void addBgm(Bgm bgm);

    Bgm queryBgmById(String id);

    List<Bgm> queryBgmList();

}
