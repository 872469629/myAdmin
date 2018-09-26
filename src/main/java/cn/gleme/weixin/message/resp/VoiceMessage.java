package cn.gleme.weixin.message.resp;

/**
 * 音频消息
 * 
 * @author liufeng
 * @date 2013-05-19
 */
public class VoiceMessage extends BaseRespMessage {
	// 媒体ID
	private String MediaId;
	// 语音格式
	private String Format;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}
}