package abcreate.fb.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import abcreate.fb.common.service.BaseService;
import abcreate.fb.modules.sys.dao.UserDao;
import abcreate.fb.modules.sys.entity.User;

/**
 * 用户Service
 */
@Service
@Transactional(readOnly = true)
public class UserService extends BaseService<UserDao, User> {
	
	/**
	 * 根据clientId查询用户
	 */
	public List<User> findByClientId(String clientId) {
		return dao.findByClientId(clientId);
	}
	
	public List<User> findByIds(List<String> idList) {
		return dao.findByIds(idList);
	}

	@Transactional(readOnly = false)
	public void save(User user) {
		super.insert(user);
	}

	@Transactional(readOnly = false)
	public void update(User user) {
		super.updateAllColumnById(user);
	}

}