package com.zhangzhe.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersReport {
    private String id;
    /**
     * 被举报用户id
     */
    private String dealUserId;
    private String dealVideoId;
    /**
     * 类型标题，让用户选择，详情见 枚举
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 举报人的id
     */
    private String userid;
    /**
     * 举报时间
     */
    private Date createDate;
}