package cn.gleme.weixin.message.push;

/**
 * 消息基类（公众帐号 -> 普通用户）
 * 
@author xjany
 * @date 2013-05-19
 */
public class BaseMessage {
	// 接收方帐号（收到的OpenID）
	private String touser;
	// 开发者微信号
	private String msgtype;
	
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
}