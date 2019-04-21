package abcreate.fb.modules.sys.dao;

import java.util.List;

import abcreate.fb.common.persistence.CrudDao;
import abcreate.fb.common.persistence.annotation.MyBatisDao;
import abcreate.fb.modules.sys.entity.Menu;

/**
 * 菜单DAO接口
 */
@MyBatisDao
public interface MenuDao extends CrudDao<Menu> {

	public List<Menu> findByParentIdsLike(Menu menu);

	public int updateParentIds(Menu menu);

	public int updateSort(Menu menu);

}