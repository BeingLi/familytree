/**
 * 
 */
package abcreate.fb.modules.fb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

//import com.hnky.baojianjv.dto.request.DictionaryDto;

import abcreate.fb.common.persistence.CrudDao;
import abcreate.fb.common.persistence.annotation.MyBatisDao;

import abcreate.fb.modules.fb.entity.Dictionary;




@MyBatisDao
public interface DictionaryDAO extends CrudDao<Dictionary> {
	
	/**
	 * 根据字典值查询
	 * @param dictValue
	 * @return
	 */
	public Dictionary findByDictValue(@Param("dictValue") String dictValue, @Param("dictCode") String dictCode);
//	{
//		String hql = "from Dictionary where dictValue=? and dictCode=?";
//		return (Dictionary) queryByHql(hql, new String[]{dictValue, dictCode});
//	}

	public List<Dictionary> findByCode(@Param("dictCode") String dictCode);
}
