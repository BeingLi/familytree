package abcreate.fb.modules.gen.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baomidou.mybatisplus.plugins.Page;
import abcreate.fb.common.persistence.PageFactory;
import abcreate.fb.common.utils.StringUtils;
import abcreate.fb.common.web.BaseController;
import abcreate.fb.modules.gen.entity.GenScheme;
import abcreate.fb.modules.gen.service.GenSchemeService;
import abcreate.fb.modules.gen.service.GenTableService;
import abcreate.fb.modules.gen.util.GenUtils;

/**
 * 生成方案Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/gen/genScheme")
public class GenSchemeController extends BaseController {

	@Autowired
	private GenSchemeService genSchemeService;

	@Autowired
	private GenTableService genTableService;

	@ModelAttribute
	public GenScheme get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return genSchemeService.get(id);
		} else {
			return new GenScheme();
		}
	}

	@RequestMapping(value = { "list", "" })
	public String list() {
		return "modules/gen/genSchemeList";
	}
	
	@ResponseBody
	@RequestMapping(value = "data")
	public Map data(GenScheme genScheme) {
		Page<GenScheme> page = genSchemeService.findPage(new PageFactory<GenScheme>().defaultPage(), genScheme);
		return jsonPage(page);
	}

	@RequestMapping(value = "form")
	public String form(GenScheme genScheme, Model model) {
		if (StringUtils.isBlank(genScheme.getPackageName())) {
			genScheme.setPackageName("abcreate.fb.modules");
		}
		model.addAttribute("genScheme", genScheme);
		model.addAttribute("config", GenUtils.getConfig());
		model.addAttribute("tableList", genTableService.findAll());
		return "modules/gen/genSchemeForm";
	}

	@RequestMapping(value = "save")
	public String save(GenScheme genScheme, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, genScheme)) {
			return form(genScheme, model);
		}

		String result = genSchemeService.save(genScheme);
		addMessage(redirectAttributes, "操作生成方案'" + genScheme.getName() + "'成功<br/>" + result);
		return "redirect:" + adminPath + "/gen/genScheme";
	}

	@RequestMapping(value = "delete")
	public String delete(GenScheme genScheme, RedirectAttributes redirectAttributes) {
		genSchemeService.delete(genScheme);
		addMessage(redirectAttributes, "删除生成方案成功");
		return "redirect:" + adminPath + "/gen/genScheme";
	}

}