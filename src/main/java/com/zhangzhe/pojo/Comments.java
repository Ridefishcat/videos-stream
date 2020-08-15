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
public class Comments {

    private String id;
    private String fatherCommentId;
    private String toUserId;

    /**
     * 视频id
     */
    private String videoId;

    /**
     * 留言者，评论的用户id
     */
    private String fromUserId;

    private Date createTime;

    /**
     * 评论内容
     */
    private String comment;
}