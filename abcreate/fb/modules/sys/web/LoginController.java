package abcreate.fb.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import abcreate.fb.common.utils.CookieUtils;
import abcreate.fb.common.web.BaseController;

/**
 * 登录Controller
 */
@Controller
@RequestMapping(value = "${adminPath}")
public class LoginController extends BaseController {

	/**
	 * 登录成功，进入管理首页
	 */
	@RequestMapping(value = "")
	public String index() {
		return "modules/sys/sysIndex";
	}

	/**
	 * 退出登录成功
	 */
	@RequestMapping(value = "logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		// 移除相关cookie
		CookieUtils.removeCookie(request, response, "userToken");
		CookieUtils.removeCookie(request, response, "userId");
		return "modules/sys/sysIndex";
	}
}