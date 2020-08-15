package com.zhangzhe.pojo.response.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublisherVideo {
	private UsersVo publisher;
	private boolean userLikeVideo;

}