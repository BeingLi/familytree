package abcreate.fb.modules.gen.web;

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
import abcreate.fb.modules.gen.entity.GenTable;
import abcreate.fb.modules.gen.service.GenTableService;
import abcreate.fb.modules.gen.util.GenUtils;

/**
 * 业务表Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/gen/genTable")
public class GenTableController extends BaseController {

	@Autowired
	private GenTableService genTableService;

	@ModelAttribute
	public GenTable get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return genTableService.get(id);
		} else {
			return new GenTable();
		}
	}

	@RequestMapping(value = { "list", "" })
	public String list() {
		return "modules/gen/genTableList";
	}

	@ResponseBody
	@RequestMapping(value = "data")
	public Map data(GenTable genTable) {
		Page<GenTable> page = genTableService.findPage(new PageFactory<GenTable>().defaultPage(), genTable);
		return jsonPage(page);
	}

	@RequestMapping(value = "form")
	public String form(GenTable genTable, Model model) {
		// 获取物理表列表
		List<GenTable> tableList = genTableService.findTableListFormDb(new GenTable());
		model.addAttribute("tableList", tableList);
		// 验证表是否存在
		if (StringUtils.isBlank(genTable.getId()) && !genTableService.checkTableName(genTable.getName())) {
			addMessage(model, "下一步失败！" + genTable.getName() + " 表已经添加！");
			genTable.setName("");
		}
		// 获取物理表字段
		else {
			genTable = genTableService.getTableFormDb(genTable);
		}
		model.addAttribute("genTable", genTable);
		model.addAttribute("config", GenUtils.getConfig());
		return "modules/gen/genTableForm";
	}

	@RequestMapping(value = "save")
	public String save(GenTable genTable, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, genTable)) {
			return form(genTable, model);
		}
		// 验证表是否已经存在
		if (StringUtils.isBlank(genTable.getId()) && !genTableService.checkTableName(genTable.getName())) {
			addMessage(model, "保存失败！" + genTable.getName() + " 表已经存在！");
			genTable.setName("");
			return form(genTable, model);
		}
		genTableService.save(genTable);
		addMessage(redirectAttributes, "保存业务表'" + genTable.getName() + "'成功");
		return "redirect:" + adminPath + "/gen/genTable";
	}

	@RequestMapping(value = "delete")
	public String delete(GenTable genTable, RedirectAttributes redirectAttributes) {
		genTableService.delete(genTable);
		addMessage(redirectAttributes, "删除业务表成功");
		return "redirect:" + adminPath + "/gen/genTable";
	}

	@RequestMapping(value = "batchDelete")
	public String batchDelete(String ids, RedirectAttributes redirectAttributes) {
		genTableService.batchDelete(ids);
		addMessage(redirectAttributes, "批量删除业务表成功");
		return "redirect:" + adminPath + "/gen/genTable";
	}

}