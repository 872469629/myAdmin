package cn.gleme.weixin.message.req;
  
/** 
 * 文本消息 
 *  
 * @author liufeng 
 * @date 2013-05-19 
 */  
public class TextMessage extends BaseReqMessage {  
    // 消息内容   
    private String Content;
  
    public String getContent() {
        return Content;  
    }  
  
   public void setContent(String content) {
        Content = content;  
    }  
}  
