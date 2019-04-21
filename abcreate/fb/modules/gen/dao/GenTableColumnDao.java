package abcreate.fb.modules.gen.dao;

import abcreate.fb.common.persistence.CrudDao;
import abcreate.fb.common.persistence.annotation.MyBatisDao;
import abcreate.fb.modules.gen.entity.GenTableColumn;

/**
 * 业务表字段DAO接口
 */
@MyBatisDao
public interface GenTableColumnDao extends CrudDao<GenTableColumn> {
	
	public void deleteByGenTableId(String genTableId);
}