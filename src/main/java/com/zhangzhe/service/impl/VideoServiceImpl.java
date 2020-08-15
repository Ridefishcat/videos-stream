package com.zhangzhe.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.zhangzhe.mapper.*;
import com.zhangzhe.pojo.Comments;
import com.zhangzhe.pojo.SearchRecords;
import com.zhangzhe.pojo.UsersLikeVideos;
import com.zhangzhe.pojo.Videos;
import com.zhangzhe.pojo.response.vo.CommentsVO;
import com.zhangzhe.pojo.response.vo.VideosVo;
import com.zhangzhe.service.VideoService;
import com.zhangzhe.utils.PagedResult;
import com.zhangzhe.utils.TimeAgoUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    VideosMapper videosMapper;

    @Autowired
    UsersLikeVideosMapper usersLikeVideosMapper;

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    SearchRecordsMapper searchRecordsMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    Sid sid;

    @Override
    public String saveVideo(Videos video) {
        String id = sid.nextShort();
        video.setId(id);
        videosMapper.insertVideo(video);
        return id;
    }

    @Override
    public PagedResult getAllVideos(Videos video,Integer isSaveRecord,Integer page, Integer pageSize) {

        String videoDesc = video.getVideoDesc();
        String userId = video.getUserId();
        if(isSaveRecord != null && isSaveRecord == 1){
            SearchRecords record = new SearchRecords();
            String recordId = sid.nextShort();
            record.setId(recordId);
            record.setContent(videoDesc);
            searchRecordsMapper.insert(record);
        }

        PageMethod.startPage(page,pageSize);
        List<VideosVo> list = videosMapper.queryAllVideos(videoDesc,userId);

        PageInfo<VideosVo> pageList = new PageInfo<>(list);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userLikeVideo(String userId, String videoId, String videoCreateId) {
        // 1. 保存用户和视频的喜欢点赞关联关系表
        String likeId = sid.nextShort();
        UsersLikeVideos ulv = new UsersLikeVideos();
        ulv.setId(likeId);
        ulv.setUserId(userId);
        ulv.setVideoId(videoId);
        usersLikeVideosMapper.insert(ulv);
        // 2. 视频喜欢数量累加
        videosMapper.addVideoLikeCount(videoId);
        // 3. 用户受喜欢数量的累加
        usersMapper.addReceiveLikeCount(videoCreateId);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userUnLikeVideo(String userId, String videoId, String videoCreateId) {
        usersLikeVideosMapper.delete(videoId);
        videosMapper.reduceVideoLikeCount(videoId);
        usersMapper.reduceReceiveLikeCount(videoCreateId);
    }

    @Override
    public List<String> getHotWords() {
        return searchRecordsMapper.getHotWords();
    }

    @Override
    public PagedResult queryMyLikeVideos(String userId, Integer page, Integer pageSize) {
        PageMethod.startPage(page, pageSize);
        List<VideosVo> list = videosMapper.queryMyLikeVideos(userId);
        PageInfo<VideosVo> pageList = new PageInfo<>(list);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setPage(page);
        pagedResult.setRecords(pageList.getTotal());

        return pagedResult;
    }

    @Override
    public PagedResult queryMyFollowVideos(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<VideosVo> list = videosMapper.queryMyFollowVideos(userId);

        PageInfo<VideosVo> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setPage(page);
        pagedResult.setRecords(pageList.getTotal());

        return pagedResult;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComment(Comments comment) {
        String id = sid.nextShort();
        comment.setId(id);
        comment.setCreateTime(new Date());
        commentMapper.insertComment(comment);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedResult getAllComments(String videoId, Integer page, Integer pageSize) {

        PageHelper.startPage(page, pageSize);

        List<CommentsVO> list = commentMapper.queryComments(videoId);

        for (CommentsVO c : list) {
            String timeAgo = TimeAgoUtils.format(c.getCreateTime());
            c.setTimeAgoStr(timeAgo);
        }

        PageInfo<CommentsVO> pageList = new PageInfo<>(list);

        PagedResult grid = new PagedResult();
        grid.setTotal(pageList.getPages());
        grid.setRows(list);
        grid.setPage(page);
        grid.setRecords(pageList.getTotal());

        return grid;
    }
}
