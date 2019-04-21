package abcreate.fb.modules.sys.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import abcreate.fb.common.config.Global;
import abcreate.fb.common.constant.Consts;
import abcreate.fb.common.rest.Result;
import abcreate.fb.common.utils.CookieUtils;
import abcreate.fb.common.utils.HttpUtils;

/**
 * 后台登陆用户token拦截器
 */
public class UserInterceptor implements HandlerInterceptor {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		// 维护登陆用户token
//		String token = request.getParameter("token");// 系统统一传入的token
//		// TODO 本地测试获取token，上线后删除
//		if (token == null) {
//			token = CookieUtils.getCookie(request, "userToken");
//			if (token == null) {
//				Map loginParam = new HashMap();
//				loginParam.put("tel", Global.getConfig("test.username"));
//				loginParam.put("pwd", Global.getConfig("test.password"));
//				Result result = HttpUtils.postForResult(Consts.LOGIN, null, loginParam);
//				if (result.getCode() != 0) {
//					logger.error("登录失败，错误信息：" + result.getMessage());
//					return true;
//				}
//				Map map = (Map) result.getResult();
//				token = (String)map.get("token");
//			}
//		}
//		String tokenCache = CookieUtils.getCookie(request, "userToken");// 缓存token
//		boolean flag = false;
//		if (tokenCache == null || !tokenCache.equals(token)) {
//			flag = true;
//			CookieUtils.setCookie(response, "userToken", token, 60 * 60 * 12);// 过期时间12小时
//		}
//		String userId = CookieUtils.getCookie(request, "userId");//缓存用户ID
//		if (userId == null || flag) {
//			Result result = HttpUtils.getForResult(Consts.FIND_USER_BY_TOKEN + token, token);
//			if (result.getCode() == 0) {
//				Map map = (Map) result.getResult();
//				userId = (String) map.get("userId");
//				CookieUtils.setCookie(response, "userId", userId, 60 * 60 * 12);// 过期时间12小时
//			}
//		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}