package abcreate.fb.modules.gen.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import abcreate.fb.common.service.BaseService;
import abcreate.fb.modules.gen.dao.GenSchemeDao;
import abcreate.fb.modules.gen.dao.GenTableColumnDao;
import abcreate.fb.modules.gen.dao.GenTableDao;
import abcreate.fb.modules.gen.entity.GenConfig;
import abcreate.fb.modules.gen.entity.GenScheme;
import abcreate.fb.modules.gen.entity.GenTable;
import abcreate.fb.modules.gen.entity.GenTableColumn;
import abcreate.fb.modules.gen.entity.GenTemplate;
import abcreate.fb.modules.gen.util.GenUtils;

/**
 * 生成方案Service
 */
@Service
@Transactional(readOnly = true)
public class GenSchemeService extends BaseService<GenSchemeDao, GenScheme> {

	@Autowired
	private GenTableDao genTableDao;
	@Autowired
	private GenTableColumnDao genTableColumnDao;

	public GenScheme get(String id) {
		return super.get(id);
	}

	public Page<GenScheme> find(Page<GenScheme> page, GenScheme genScheme) {
		GenUtils.getTemplatePath();
		return super.findPage(page, genScheme);
	}

	@Transactional(readOnly = false)
	public String save(GenScheme genScheme) {
		super.insertOrUpdate(genScheme);
		// 生成代码
		if ("1".equals(genScheme.getFlag())) {
			return generateCode(genScheme);
		}
		return "";
	}

	@Transactional(readOnly = false)
	public void delete(GenScheme genScheme) {
		super.delete(genScheme);
	}

	private String generateCode(GenScheme genScheme) {

		StringBuilder result = new StringBuilder();

		// 查询主表及字段列
		GenTable genTable = genTableDao.get(genScheme.getGenTable().getId());
		genTable.setColumnList(genTableColumnDao.findList(new GenTableColumn(new GenTable(genTable.getId()))));

		// 获取所有代码模板
		GenConfig config = GenUtils.getConfig();

		// 获取模板列表
		List<GenTemplate> templateList = GenUtils.getTemplateList(config, genScheme.getCategory());

		// 生成主表模板代码
		genScheme.setGenTable(genTable);
		Map<String, Object> model = GenUtils.getDataModel(genScheme);
		for (GenTemplate tpl : templateList) {
			result.append(GenUtils.generateToFile(tpl, model, genScheme.getReplaceFile()));
		}
		return result.toString();
	}
}