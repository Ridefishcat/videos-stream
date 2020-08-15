package com.zhangzhe.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersFans {

    private String id;
    /**
     * 用户
     */
    private String userId;
    /**
     * 粉丝
     */
    private String fanId;
}