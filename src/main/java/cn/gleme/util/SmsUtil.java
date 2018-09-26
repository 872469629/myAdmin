package cn.gleme.util;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;

public class SmsUtil {

	private final static Logger log = LoggerFactory.getLogger(SmsUtil.class);

	@Value("${sms.appid}")
	private static Integer APPID = 1400074878;

	@Value("${sms.appkey}")
	private static String APPKEY = "925916c1e7b592489fc64d27bf57bc63";

	/** 10分钟 */
	@Value("${sms.validity}")
	public static String MINUTE = "10";

	@Value("${sms.modelid}")
	public static Integer modelid = 95190;

	/**
	 * 发送
	 * 
	 * @param code
	 * @return
	 */
	public static String sendVerifyCode(String code, String moblie) {
		try {
			SmsSingleSender sender = new SmsSingleSender(APPID, APPKEY);
			ArrayList<String> params = new ArrayList<>();
			params.add(code);
			params.add(MINUTE);
			SmsSingleSenderResult result = sender.sendWithParam("86", moblie, modelid, params, "", "", "");
			log.info(com.alibaba.fastjson.JSON.toJSONString(result));
			if (result.errMsg.equals("OK")) {
				return null;
			} else {
				return result.errMsg;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("发送短信出错：{}", e.getMessage());
			return "发送验证码失败，请检查手机号格式是否有误！";
		}
	}

	public static void main(String[] args) {
		try {
			SmsSingleSender sender = new SmsSingleSender(1400074878, "925916c1e7b592489fc64d27bf57bc63");
			ArrayList<String> params = new ArrayList<>();
			params.add("9877");
			params.add("10");
			// SmsSingleSenderResult result = sender.send(0, "86",
			// "15920960245", "验证码333，您正在绑定用户手机号，请于5分钟内填写，感谢您的支持！ ", "", "");
			SmsSingleSenderResult result = sender.sendWithParam("86", "15920960245", 95190, params, "", "", "");
			System.out.print(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
