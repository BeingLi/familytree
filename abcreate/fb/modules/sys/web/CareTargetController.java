package abcreate.fb.modules.sys.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import abcreate.fb.common.constant.Consts;
import abcreate.fb.common.rest.Result;
import abcreate.fb.common.utils.HttpUtils;
import abcreate.fb.common.web.BaseController;
import abcreate.fb.modules.sys.utils.UserUtils;

/**
 * 保健对象Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/careTarget")
public class CareTargetController extends BaseController {

	@RequestMapping(value = "")
	public String list(Model model, String type) {
		Result result = HttpUtils.getForResult(Consts.FIND_ALL_ORGANIZATIONS);
		if (result.getCode() == 0) {
			List resultList = (List) result.getResult();
			model.addAttribute("orgList", resultList);// 单位列表
		}
		model.addAttribute("type", type);// 1:radio,2:checkbox,0:不显示
		return "modules/sys/careTargetList";
	}

	@ResponseBody
	@RequestMapping(value = "data")
	public Map listData(HttpServletRequest request, String sexCode, String gradeCode, String startAge, String endAge,
			String orgId) {
		int limit = Integer.valueOf(request.getParameter("limit"));
		int offset = Integer.valueOf(request.getParameter("offset"));
		String workerId = UserUtils.getUserId();
		Map resultMap = UserUtils.getCareTarget(sexCode, gradeCode, startAge, endAge, orgId, workerId, offset / limit + 1, limit);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", resultMap.get("total"));
		map.put("rows", resultMap.get("rows"));
		return map;
	}

	@RequestMapping(value = "{idCard}", method = RequestMethod.GET)
	public void getCareTargetInfo(HttpServletResponse response, @PathVariable String idCard) {
		Result result = HttpUtils.getForResult(Consts.FIND_HEALTHCARETARGET_BY_IDCARD + idCard);
		if (result != null) {
			if (result.getCode() != 0) {
				logger.error("根据身份证号获取保健对象失败，错误信息：" + result.getMessage());
				return;
			}
			renderString(response, result.getResult());
		}
	}

}