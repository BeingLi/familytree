package abcreate.fb.common.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import abcreate.fb.common.persistence.BaseEntity;
import abcreate.fb.common.persistence.CrudDao;
import abcreate.fb.common.persistence.DataEntity;
import abcreate.fb.common.utils.StringUtils;
import abcreate.fb.modules.sys.utils.UserUtils;

/**
 * Service基类
 */
@Transactional(readOnly = true)
public abstract class BaseService<M extends CrudDao<T>, T extends BaseEntity<T>> extends ServiceImpl<M, T> {

	/**
	 * 持久层对象
	 */
	@Autowired
	protected M dao;

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 获取单条数据
	 * 
	 * @param id
	 */
	public T get(String id) {
		return dao.get(id);
	}

	/**
	 * 查询列表数据
	 * 
	 * @param entity
	 */
	public List<T> findList(T entity) {
		return dao.findList(entity);
	}

	/**
	 * 查询所有数据列表
	 * 
	 * @param entity
	 */
	public List<T> findAllList(T entity) {
		return dao.findAllList(entity);
	}

	/**
	 * 查询分页数据
	 * 
	 * @param page 分页对象
	 * @param entity
	 */
	public Page<T> findPage(Page<T> page, T entity) {
		entity.setPage(page);
		page.setRecords(dao.findList(page, entity));
		return page;
	}

	@Transactional(readOnly = false)
	public boolean insertOrUpdate(T entity) {
		if (entity instanceof DataEntity) {
			DataEntity de = (DataEntity) entity;
			Date date = new Date();
			String userId = UserUtils.getUserId();
			if (StringUtils.isBlank(de.getId())) {// 插入之前执行操作
				de.setCreateBy(userId);
				de.setCreateDate(date);
			}
			de.setUpdateBy(userId);
			de.setUpdateDate(date);
		}
		return super.insertOrUpdate(entity);
	}

	/**
	 * 删除数据（逻辑删除，更新del_flag字段为1）
	 * 
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void delete(T entity) {
		super.deleteById(entity.getId());// 使用mybatis-plus自带的逻辑删除
	}

}