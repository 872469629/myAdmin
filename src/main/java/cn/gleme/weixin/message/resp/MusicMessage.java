package cn.gleme.weixin.message.resp;

/**
 * 音乐消息
 * 
@author xjany
 * @date 2013-05-19
 */
public class MusicMessage extends BaseRespMessage {
	// 音乐
	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
}