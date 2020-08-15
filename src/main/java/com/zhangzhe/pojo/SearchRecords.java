package com.zhangzhe.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRecords {

    private String id;
    /**
     * 搜索的内容
     */
    private String content;
}