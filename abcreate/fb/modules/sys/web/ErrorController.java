package abcreate.fb.modules.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import abcreate.fb.common.web.BaseController;

/**
 * errorController
 */
@Controller
@RequestMapping(value = "${adminPath}/error")
public class ErrorController extends BaseController {
	
	@RequestMapping(value = "{errorCode}")
	public String error500(@PathVariable String errorCode) {
		return "error/" + errorCode;
	}

}