package abcreate.fb.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import abcreate.fb.common.service.BaseService;
import abcreate.fb.common.utils.CacheUtils;
import abcreate.fb.modules.sys.dao.MenuDao;
import abcreate.fb.modules.sys.entity.Menu;
import abcreate.fb.modules.sys.utils.UserUtils;

/**
 * 菜单管理
 */
@Service
@Transactional(readOnly = true)
public class MenuService extends BaseService<MenuDao, Menu> {

	public Menu getMenu(String id) {
		return super.get(id);
	}

	public List<Menu> findAllMenu() {
		return UserUtils.getMenuList();
	}

	@Transactional(readOnly = false)
	public void saveMenu(Menu menu) {

		// 获取父节点实体
		menu.setParent(this.getMenu(menu.getParent().getId()));

		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = menu.getParentIds();

		// 设置新的父节点串
		menu.setParentIds(menu.getParent().getParentIds() + menu.getParent().getId() + ",");

		// 保存或更新实体
		super.insertOrUpdate(menu);

		// 更新子节点 parentIds
		Menu m = new Menu();
		m.setParentIds("%," + menu.getId() + ",%");
		List<Menu> list = dao.findByParentIdsLike(m);
		for (Menu e : list) {
			e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
			dao.updateParentIds(e);
		}
		// 清除用户菜单缓存
		CacheUtils.remove(UserUtils.CACHE_MENU_LIST);
	}

	@Transactional(readOnly = false)
	public void updateMenuSort(Menu menu) {
		dao.updateSort(menu);
		// 清除用户菜单缓存
		CacheUtils.remove(UserUtils.CACHE_MENU_LIST);
	}

	@Transactional(readOnly = false)
	public void deleteMenu(Menu menu) {
		dao.delete(menu);
		// 清除用户菜单缓存
		CacheUtils.remove(UserUtils.CACHE_MENU_LIST);
	}

}