package abcreate.fb.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import abcreate.fb.common.mapper.JsonMapper;

/**
 * 短信发送工具类
 */
public class SmsUtils {

	private static Logger logger = LoggerFactory.getLogger(SmsUtils.class);

	/**
	 * 短信发送
	 * 
	 * @param mobileList 目标手机号码列表，多个号码使用","分隔
	 * @param content 短信内容
	 * @return 失败原因
	 */
	public static Map<String, String> sendSms(String mobileList, String content) {
		Map<String, String> header = new HashMap<String, String>();
		header.put("Authorization", "Basic " + Encodes.encodeBase64("api:key-8ce17cd042f14f8233442409ea858c09"));
		Map<String, String> param = new HashMap<String, String>();
		param.put("mobile_list", mobileList);
		param.put("message", content + "【陕西保健局】");
		String returnContent = HttpUtils.doPost("http://sms-api.luosimao.com/v1/send_batch.json", header, param, false);
		Map<String, String> map = new HashMap<String, String>();
		if (returnContent == null) {
			map.put("error", "连接短信服务器失败");
			return map;
		}
		Map resultMap = (Map) JsonMapper.fromJsonString(returnContent, Map.class);
		// 发送失败
		if ((Integer) resultMap.get("error") != 0) {
			logger.error("错误码:" + resultMap.get("error") + ",错误码描述:" + (String) resultMap.get("msg"));
			map.put("error", (String) resultMap.get("msg"));
			return map;
		}
		// 发送成功
		map.put("error", "");
		map.put("batchId", (String) resultMap.get("batch_id"));
		return map;
	}

}