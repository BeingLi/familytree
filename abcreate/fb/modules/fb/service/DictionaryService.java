package abcreate.fb.modules.fb.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import abcreate.fb.common.service.BaseService;
import abcreate.fb.modules.fb.dao.DictionaryDAO;
import abcreate.fb.modules.fb.entity.Dictionary;
@Service
@Transactional(readOnly = true)
public class DictionaryService extends BaseService<DictionaryDAO, Dictionary>{
	@Resource
	private DictionaryDAO dictionaryDAO;
	public Dictionary findByDictValue(String dictValue, String dictCode) {
		return dictionaryDAO.findByDictValue(dictValue,dictCode);
	}
	
	public List<Dictionary> findByCode(String dictCode){
		return dictionaryDAO.findByCode(dictCode);
	}
}

