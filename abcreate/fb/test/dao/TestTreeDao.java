package abcreate.fb.test.dao;

import abcreate.fb.common.persistence.TreeDao;
import abcreate.fb.common.persistence.annotation.MyBatisDao;
import abcreate.fb.test.entity.TestTree;

/**
 * 树结构生成DAO接口
 */
@MyBatisDao
public interface TestTreeDao extends TreeDao<TestTree> {

}