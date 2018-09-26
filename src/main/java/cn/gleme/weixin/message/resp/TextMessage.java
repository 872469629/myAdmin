package cn.gleme.weixin.message.resp;

/**
 * 文本消息
 * 
@author xjany
 * @date 2013-05-19
 */
public class TextMessage extends BaseRespMessage {
	// 回复的消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}