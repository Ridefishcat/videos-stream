package com.zhangzhe.mapper;

import com.zhangzhe.pojo.Comments;
import com.zhangzhe.pojo.response.vo.CommentsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    void insertComment(Comments comment);

    List<CommentsVO> queryComments(String videoId);
}
