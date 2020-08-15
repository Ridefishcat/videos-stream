package com.zhangzhe.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bgm {
    @Id
    private String id;

    private String author;

    private String name;

    /**
     * 播放地址
     */
    private String path;
}