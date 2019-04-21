package abcreate.fb.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import abcreate.fb.common.service.BaseService;
import abcreate.fb.common.utils.CacheUtils;
import abcreate.fb.modules.sys.dao.DictDao;
import abcreate.fb.modules.sys.entity.Dict;
import abcreate.fb.modules.sys.utils.DictUtils;

/**
 * 字典Service
 */
@Service
@Transactional(readOnly = true)
public class DictService extends BaseService<DictDao, Dict> {

	/**
	 * 查询字段类型列表
	 */
	public List<String> findTypeList() {
		return dao.findTypeList(new Dict());
	}

	@Transactional(readOnly = false)
	public void save(Dict dict) {
		super.insertOrUpdate(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Transactional(readOnly = false)
	public void delete(Dict dict) {
		super.delete(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

}