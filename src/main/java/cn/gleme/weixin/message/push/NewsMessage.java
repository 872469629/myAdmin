package cn.gleme.weixin.message.push;


/**
 * 文本消息
 * 
@author xjany
 * @date 2013-05-19
 */
public class NewsMessage extends BaseMessage {
	// 多条图文消息信息，默认第一个item为大图
	private Articles news;

	public Articles getNews() {
		return news;
	}

	public void setNews(Articles news) {
		this.news = news;
	}
	
}