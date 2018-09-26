/*
 * Copyright 2005-2013 znzv.cn. All rights reserved.
 * Support: http://www.znzv.cn

 */
package cn.gleme.weixin.pojo;

import lombok.Data;

/**
 * 微信返回实体 bean
 * 
 * @author xinegle 公司
 * @version 3.0
 */
@Data
public class WeChat {
	private String subscribe; //是否关注
	private String openid; //微信ID即openId
	private String nickname;
	private Integer sex;
	private String language;
	private String city;
	private String country;
	private String province;
	private String headimgurl;
	private String subscribe_time;//关注时间
	private String unionid;//关联Id
}