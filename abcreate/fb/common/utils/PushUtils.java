package abcreate.fb.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gexin.rp.sdk.base.IBatch;
import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.google.common.collect.Lists;
import abcreate.fb.common.mapper.JsonMapper;

/**
 * APP消息推送工具类
 */
public class PushUtils {
	private static String appId = "WMXwQB4iX36dq5F9PAMruA";
	private static String appKey = "SMkydtXkY98W6pOvHFup68";
	private static String masterSecret = "hVQWvMvUe0AJ44H92iZPe1";
	private static String host = "http://sdk.open.api.igexin.com/apiex.htm";
	
	private static Logger logger = LoggerFactory.getLogger(PushUtils.class);

	/**
	 * 构建透传消息模板
	 */
	private static TransmissionTemplate makeTransmissionTemplate(String title, String url) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(2);
		Map<String, String> contentMap = new HashMap<String, String>();
		contentMap.put("title", title);
		contentMap.put("url", url);
		template.setTransmissionContent(JsonMapper.toJsonString(contentMap));
		APNPayload payload = new APNPayload();
		// 在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
		payload.setAutoBadge("+1");
		payload.setContentAvailable(1);
		payload.setSound("default");
		// 简单模式APNPayload.SimpleMsg
		payload.setAlertMsg(new APNPayload.SimpleAlertMsg(title));

		template.setAPNInfo(payload);
		return template;
	}

	/**
	 * 对单个用户推送消息
	 */
	public static void pushToSingle(String clientId, String title, String url) {
		IGtPush push = new IGtPush(host, appKey, masterSecret);
		TransmissionTemplate template = makeTransmissionTemplate(title, url);
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		message.setPushNetWorkType(0);
		Target target = new Target();
		target.setAppId(appId);
		target.setClientId(clientId);
		IPushResult ret = null;
		try {
			ret = push.pushMessageToSingle(message, target);
		} catch (RequestException e) {
			logger.error(e.getMessage());
			ret = push.pushMessageToSingle(message, target, e.getRequestId());
		}
		if (ret != null) {
			logger.info(ret.getResponse().toString());
		} else {
			logger.error("推送服务器响应异常");
		}
	}

	/**
	 * 批量单推
	 */
	public static void pushBatch(List<String> clientIds, String title, String url) throws Exception {
		IIGtPush push = new IGtPush(host, appKey, masterSecret);
		IBatch batch = push.getBatch();
		SingleMessage message = new SingleMessage();
		TransmissionTemplate template = makeTransmissionTemplate(title, url);
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		message.setPushNetWorkType(0);
		for (String clientId : clientIds) {
			// 设置推送目标
			Target target = new Target();
			target.setAppId(appId);
			target.setClientId(clientId);
			batch.add(message, target);
		}
		IPushResult ret = batch.submit();
		if (ret != null) {
			logger.info(ret.getResponse().toString());
		} else {
			logger.error("推送服务器响应异常");
		}
	}

	public static void main(String[] args) throws Exception {
		// pushToSingle("4c124d76c4ca56bbef77daf4596d2fcd");
		pushBatch(Lists.newArrayList("56e21a8887b5596ae02b5b317f1f64d0"), "百度", "http://www.baidu.com");
	}
}