package cn.gleme.weixin.message.mass;

import java.util.ArrayList;
import java.util.List;

/**
 * 图文model
 * 
@author xjany
 * @date 2013-05-19
 */
public class Articles {
	private List<Article> articles = new ArrayList<>();

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

}