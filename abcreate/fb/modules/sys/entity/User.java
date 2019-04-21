package abcreate.fb.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import abcreate.fb.common.persistence.BaseEntity;

/**
 * 用户Entity
 */
@TableName("sys_user")
public class User extends BaseEntity<User> {

	private static final long serialVersionUID = 1L;
	private String clientId; // 个推clientId

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}