package abcreate.fb.common.persistence.typeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import abcreate.fb.common.persistence.BaseEntity;

/**
 * 解决数据库中的数据类型(字符串形式的ID)和Java中的数据类型(对象)之间的转化问题
 */
@MappedJdbcTypes({ JdbcType.VARCHAR })
@MappedTypes({ BaseEntity.class })
public class EntityTypeHandler extends BaseTypeHandler<BaseEntity> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, BaseEntity parameter, JdbcType jdbcType)
			throws SQLException {
		// 重新定义要写往数据库的数据
		ps.setString(i, parameter.getId());
	}

	@Override
	public BaseEntity getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return null;
	}

	@Override
	public BaseEntity getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public BaseEntity getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return null;
	}

}