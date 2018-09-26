package cn.gleme.weixin.message.mass;

/**
 * 图文model
 * 
@author xjany
 * @date 2013-05-19
 */
public class Article {
	// 图文消息名称
	private String title;
	// 图文消息描述
	private String digest;
	// 图文消息描内容
	private String content;
	// 图片资源ID
	private String thumb_media_id;
	private String author;
	// 点击图文消息跳转链接
	private String content_source_url;
	// 是否显示封面
	private String show_cover_pic ;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getThumb_media_id() {
		return thumb_media_id;
	}

	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent_source_url() {
		return content_source_url;
	}

	public void setContent_source_url(String content_source_url) {
		this.content_source_url = content_source_url;
	}

	public String getShow_cover_pic() {
		return show_cover_pic;
	}

	public void setShow_cover_pic(String show_cover_pic) {
		this.show_cover_pic = show_cover_pic;
	}
}