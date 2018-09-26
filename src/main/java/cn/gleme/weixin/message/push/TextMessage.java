package cn.gleme.weixin.message.push;


/**
 * 文本消息
 * 
@author xjany
 * @date 2013-05-19
 */
public class TextMessage extends BaseMessage {
	// 回复的消息内容
	private Text text;

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
	
}