package abcreate.fb.common.persistence;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * DAO支持类实现
 * @param <T>
 */
public interface CrudDao<T> extends BaseMapper<T> {

	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id);

	/**
	 * 查询数据列表
	 * @param entity
	 */
	public List<T> findList(T entity);
	
	/**
	 * 分页查询数据列表
	 * @param page
	 * @param entity
	 */
	public List<T> findList(Pagination page, T entity);

	/**
	 * 查询所有数据列表
	 * @param entity
	 * @return
	 */
	public List<T> findAllList(T entity);
	
	/**
	 * 删除数据
	 * @param entity
	 * @return
	 */
	public int delete(T entity);

}