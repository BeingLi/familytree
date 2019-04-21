package abcreate.fb.test.dao;

import abcreate.fb.common.persistence.CrudDao;
import abcreate.fb.common.persistence.annotation.MyBatisDao;
import abcreate.fb.test.entity.TestData;

/**
 * 单表生成DAO接口
 */
@MyBatisDao
public interface TestDataDao extends CrudDao<TestData> {

}