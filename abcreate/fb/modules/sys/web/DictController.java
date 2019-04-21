package abcreate.fb.modules.sys.web;

import java.util.List;
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
import abcreate.fb.modules.sys.entity.Dict;
import abcreate.fb.modules.sys.service.DictService;

/**
 * 字典Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/dict")
public class DictController extends BaseController {

	@Autowired
	private DictService dictService;

	@ModelAttribute
	public Dict get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return dictService.selectById(id);
		} else {
			return new Dict();
		}
	}

	@RequestMapping(value = { "list", "" })
	public String list(Model model) {
		List<String> typeList = dictService.findTypeList();
		model.addAttribute("typeList", typeList);
		return "modules/sys/dictList";
	}

	@ResponseBody
	@RequestMapping(value = "data")
	public Map listData(Dict dict) {
		Page<Dict> page = dictService.findPage(new PageFactory<Dict>().defaultPage(), dict);
		return jsonPage(page);
	}
	
	@ResponseBody
	@RequestMapping(value = "query")
	public List<Dict> query(@RequestParam(required=false) String type) {
		Dict dict = new Dict();
		dict.setType(type);
		return dictService.findList(dict);
	}

	@RequestMapping(value = "form")
	public String form(Dict dict, Model model) {
		model.addAttribute("dict", dict);
		return "modules/sys/dictForm";
	}

	@RequestMapping(value = "save")
	public String save(Dict dict, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dict)) {
			return form(dict, model);
		}
		dictService.save(dict);
		addMessage(redirectAttributes, "保存字典'" + dict.getLabel() + "'成功");
		return "redirect:" + adminPath + "/sys/dict?type=" + dict.getType();
	}

	@RequestMapping(value = "delete")
	public String delete(Dict dict, RedirectAttributes redirectAttributes) {
		dictService.delete(dict);
		addMessage(redirectAttributes, "删除字典成功");
		return "redirect:" + adminPath + "/sys/dict?type=" + dict.getType();
	}

}