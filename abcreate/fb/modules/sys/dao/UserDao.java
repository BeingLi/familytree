package abcreate.fb.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import abcreate.fb.common.persistence.CrudDao;
import abcreate.fb.common.persistence.annotation.MyBatisDao;
import abcreate.fb.modules.sys.entity.User;

/**
 * 用户DAO接口
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {

	public List<User> findByClientId(String clientId);
	
	public List<User> findByIds(@Param("idList") List<String> idList);

}